package com.vko.labworkproducer.chrome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationTest {

    static WebDriver driver;

    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
    }

    @BeforeEach
    void setUpEach() {
        driver.get("http://localhost:3000/register");
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    public void successfulRegistrationTest() {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement confirmPasswordInput = driver.findElement(By.xpath("//label[text()='Подтверждение пароля:']/following-sibling::input"));

        usernameInput.sendKeys("testchromeuser");
        passwordInput.sendKeys("password123");
        confirmPasswordInput.sendKeys("password123");

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Зарегистрироваться']"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Регистрация выполнена успешно", secondChildDiv.getText());
    }

    @Test
    @Order(2)
    public void incorrectUsernameTest() {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement confirmPasswordInput = driver.findElement(By.xpath("//label[text()='Подтверждение пароля:']/following-sibling::input"));

        usernameInput.sendKeys("1");
        passwordInput.sendKeys("password123");
        confirmPasswordInput.sendKeys("password123");

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Зарегистрироваться']"));
        submitButton.click();

        WebElement errorToast = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));
        assertEquals("Имя пользователя должно содержать от 3 до 32 символов", errorToast.getText());
    }

    @Test
    @Order(3)
    public void incorrectPasswordTest() {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement confirmPasswordInput = driver.findElement(By.xpath("//label[text()='Подтверждение пароля:']/following-sibling::input"));

        usernameInput.sendKeys("testchromeuser");
        passwordInput.sendKeys("1");
        confirmPasswordInput.sendKeys("1");

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Зарегистрироваться']"));
        submitButton.click();

        WebElement errorToast = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));
        assertEquals("Пароль должен содержать не менее 6 символов", errorToast.getText());
    }

    @Test
    @Order(4)
    public void differentPasswordsTest() {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement confirmPasswordInput = driver.findElement(By.xpath("//label[text()='Подтверждение пароля:']/following-sibling::input"));

        usernameInput.sendKeys("testchromeuser");
        passwordInput.sendKeys("password123");
        confirmPasswordInput.sendKeys("password321");

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Зарегистрироваться']"));
        submitButton.click();

        WebElement errorToast = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));
        assertEquals("Введенные пароли не совпадают", errorToast.getText());
    }

    @Test
    @Order(5)
    public void usernameAlreadyExistsTest() {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement confirmPasswordInput = driver.findElement(By.xpath("//label[text()='Подтверждение пароля:']/following-sibling::input"));

        usernameInput.sendKeys("testchromeuser");
        passwordInput.sendKeys("password123");
        confirmPasswordInput.sendKeys("password123");

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Зарегистрироваться']"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Такой пользователь уже существует", secondChildDiv.getText());
    }

}
