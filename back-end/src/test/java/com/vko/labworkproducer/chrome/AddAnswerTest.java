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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddAnswerTest {

    static WebDriver driver;

    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:3000/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.sendKeys("testuser");
        passwordInput.sendKeys("testuser");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        driver.navigate().refresh();

        List<WebElement> labworkItems = driver.findElements(By.className("labwork-item"));
        labworkItems.get(0).click();
    }

    @BeforeEach
    void setUpBeforeEach() {
        WebElement variantInput = driver.findElement(By.id("variantInput"));
        variantInput.sendKeys("1");

        WebElement taskElement = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
    }

    @AfterEach
    void setUpAfterEach() {
        driver.navigate().refresh();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    public void addAnswerTest() {
        WebElement addAnswerButton = driver.findElement(By.className("add-answer"));
        addAnswerButton.click();

        WebElement descriptionTextArea = driver.findElement(By.className("answer-description-textarea"));
        descriptionTextArea.sendKeys("Тестовое описание");

        WebElement submitButton = driver.findElement(By.cssSelector("button.submit-button"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Ответ успешно добавлен", secondChildDiv.getText());
    }

    @Test
    @Order(2)
    public void emptyDataTest() {
        WebElement addAnswerButton = driver.findElement(By.className("add-answer"));
        addAnswerButton.click();

        WebElement submitButton = driver.findElement(By.cssSelector("button.submit-button"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Необходимо добавить файл или описание", secondChildDiv.getText());
    }

}
