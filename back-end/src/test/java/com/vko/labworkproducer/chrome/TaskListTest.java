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

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskListTest {

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
    public void topicListTest() {
        WebElement expandButton = driver.findElement(By.id("expand"));
        expandButton.click();

        WebElement addLabWorkButton = driver.findElement(By.id("edit-tasks"));
        addLabWorkButton.click();

        WebElement topicSelect = driver.findElement(By.cssSelector("select"));
        topicSelect.click();

        WebElement firstTopicOption = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("option:nth-child(2)")));
        firstTopicOption.click();

        List<WebElement> topicItems = driver.findElements(By.className("task-item"));
        assertFalse(topicItems.isEmpty());
    }

}
