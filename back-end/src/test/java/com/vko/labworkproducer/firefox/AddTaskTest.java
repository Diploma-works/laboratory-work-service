package com.vko.labworkproducer.firefox;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddTaskTest {

    static WebDriver driver;

    @BeforeAll
    static void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("http://localhost:3000/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.sendKeys("SUPERUSER");
        passwordInput.sendKeys("SUPERUSER");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        driver.navigate().refresh();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @AfterEach
    void setUpAfterEach() {
        driver.navigate().refresh();
    }

    @Test
    @Order(1)
    public void successfulAddTaskTest() {
        WebElement expandButton = driver.findElement(By.id("expand"));
        expandButton.click();

        WebElement addLabWorkButton = driver.findElement(By.id("edit-tasks"));
        addLabWorkButton.click();

        WebElement topicSelect = driver.findElement(By.cssSelector("select"));
        topicSelect.click();

        WebElement firstTopicOption = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("option:nth-child(2)")));
        firstTopicOption.click();

        WebElement addAnswerButton = driver.findElement(By.className("add-task-button"));
        addAnswerButton.click();

        WebElement descriptionTextArea = driver.findElement(By.className("new-task-input"));
        descriptionTextArea.sendKeys("Firefox");

        WebElement submitButton = driver.findElement(By.cssSelector("button.add-button"));
        submitButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Задание успешно добавлено", secondChildDiv.getText());
    }

}
