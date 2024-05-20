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
public class DeleteTaskTest {

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
    public void successfulDeleteTaskTest() {
        WebElement expandButton = driver.findElement(By.id("expand"));
        expandButton.click();

        WebElement addLabWorkButton = driver.findElement(By.id("edit-tasks"));
        addLabWorkButton.click();

        WebElement topicSelect = driver.findElement(By.cssSelector("select"));
        topicSelect.click();

        WebElement firstTopicOption = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("option:nth-child(2)")));
        firstTopicOption.click();

        List<WebElement> listItems = driver.findElements(By.cssSelector("li.task-item"));

        WebElement targetButton = null;

        for (WebElement item : listItems) {
            WebElement input = item.findElement(By.cssSelector("input.added-task"));
            if (input.getAttribute("value").equals("Firefox")) {
                targetButton = item.findElement(By.cssSelector("button.remove-button"));
                break;
            }
        }

        targetButton.click();
        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Задание успешно удалено", secondChildDiv.getText());
    }

    @Test
    @Order(2)
    public void usedTaskTest() {
        WebElement expandButton = driver.findElement(By.id("expand"));
        expandButton.click();

        WebElement addLabWorkButton = driver.findElement(By.id("edit-tasks"));
        addLabWorkButton.click();

        WebElement topicSelect = driver.findElement(By.cssSelector("select"));
        topicSelect.click();

        WebElement firstTopicOption = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("option:nth-child(2)")));
        firstTopicOption.click();

        List<WebElement> listItems = driver.findElements(By.cssSelector("li.task-item"));

        WebElement targetButton = null;

        for (WebElement item : listItems) {
            WebElement input = item.findElement(By.cssSelector("input.added-task"));
            if (input.getAttribute("value").equals("Задание 1")) {
                targetButton = item.findElement(By.cssSelector("button.remove-button"));
                break;
            }
        }

        targetButton.click();
        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Задание используется в сгенерированном варианте задания", secondChildDiv.getText());
    }

}
