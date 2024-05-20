package com.vko.labworkproducer.firefox;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest {

    static WebDriver driver;

    @BeforeAll
    static void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
    }

    @BeforeEach
    void setUpBeforeEach() {
        driver.get("http://localhost:3000/login");
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    public void validLoginTest() {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.sendKeys("testfirefoxuser");
        passwordInput.sendKeys("password123");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertTrue(secondChildDiv.getText().equals("Вход выполнен успешно"));

        driver.get("http://localhost:3000/login");
        WebElement usernameDiv = driver.findElement(By.className("header-username"));
        Actions actions = new Actions(driver);
        actions.moveToElement(usernameDiv).perform();
        WebElement unLoginButton = driver.findElement(By.className("dropdown-menu"));
        unLoginButton.click();
    }

    @Test
    @Order(2)
    public void incorrectUsernameTest() {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.sendKeys("1");
        passwordInput.sendKeys("password123");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertTrue(secondChildDiv.getText().equals("Имя пользователя должно содержать от 3 до 32 символов"));
    }

    @Test
    @Order(3)
    public void incorrectPasswordTest() {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.sendKeys("testfirefoxuser");
        passwordInput.sendKeys("1");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertTrue(secondChildDiv.getText().equals("Пароль должен содержать не менее 6 символов"));
    }

    @Test
    @Order(4)
    public void notExistsUsernameTest() {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.sendKeys("notexists");
        passwordInput.sendKeys("password123");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertTrue(secondChildDiv.getText().equals("Неправильно введен логин или пароль"));
    }

    @Test
    @Order(5)
    public void notValidPasswordTest() {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.sendKeys("testfirefoxuser");
        passwordInput.sendKeys("password1234");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Неправильно введен логин или пароль", secondChildDiv.getText());
    }

}
