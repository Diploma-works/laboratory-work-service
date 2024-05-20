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
public class AddLabWorkTest {

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
    public void successfulAddLabWorkTest() {
        WebElement expandButton = driver.findElement(By.id("expand"));
        expandButton.click();

        WebElement addLabWorkButton = driver.findElement(By.id("add-labwork"));
        addLabWorkButton.click();

        WebElement nameInput = driver.findElement(By.id("labwork-name"));
        nameInput.sendKeys("Firefox LabWork");

        WebElement descriptionInput = driver.findElement(By.id("labwork-description"));
        descriptionInput.sendKeys("Описание новой лабораторной работы");

        WebElement topicSelect = driver.findElement(By.cssSelector("select"));
        topicSelect.click();

        WebElement firstTopicOption = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("option:nth-child(2)")));
        firstTopicOption.click();

        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(), 'Добавить')]"));
        addButton.click();

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Добавить лабораторную работу')]"));
        addNewLabWorkButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Лабораторная работа успешно добавлена", secondChildDiv.getText());
    }

    @Test
    @Order(2)
    public void labWorkNameAlreadyExistsTest() {
        WebElement expandButton = driver.findElement(By.id("expand"));
        expandButton.click();

        WebElement addLabWorkButton = driver.findElement(By.id("add-labwork"));
        addLabWorkButton.click();

        WebElement nameInput = driver.findElement(By.id("labwork-name"));
        nameInput.sendKeys("Firefox LabWork");

        WebElement descriptionInput = driver.findElement(By.id("labwork-description"));
        descriptionInput.sendKeys("Описание новой лабораторной работы");

        WebElement topicSelect = driver.findElement(By.cssSelector("select"));
        topicSelect.click();

        WebElement firstTopicOption = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("option:nth-child(2)")));
        firstTopicOption.click();

        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(), 'Добавить')]"));
        addButton.click();

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Добавить лабораторную работу')]"));
        addNewLabWorkButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Лабораторная работа с таким названием уже существует", secondChildDiv.getText());
    }

    @Test
    @Order(3)
    public void emptyNameTest() {
        WebElement expandButton = driver.findElement(By.id("expand"));
        expandButton.click();

        WebElement addLabWorkButton = driver.findElement(By.id("add-labwork"));
        addLabWorkButton.click();

        WebElement descriptionInput = driver.findElement(By.id("labwork-description"));
        descriptionInput.sendKeys("Описание новой лабораторной работы");

        WebElement topicSelect = driver.findElement(By.cssSelector("select"));
        topicSelect.click();
        WebElement firstTopicOption = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("option:nth-child(2)")));
        firstTopicOption.click();

        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(), 'Добавить')]"));
        addButton.click();

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Добавить лабораторную работу')]"));
        addNewLabWorkButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Необходимо заполнить название лабораторной работы", secondChildDiv.getText());
    }

    @Test
    @Order(4)
    public void emptyDescriptionTest() {
        WebElement expandButton = driver.findElement(By.id("expand"));
        expandButton.click();

        WebElement addLabWorkButton = driver.findElement(By.id("add-labwork"));
        addLabWorkButton.click();

        WebElement nameInput = driver.findElement(By.id("labwork-name"));
        nameInput.sendKeys("Firefox LabWork");

        WebElement topicSelect = driver.findElement(By.cssSelector("select"));
        topicSelect.click();
        WebElement firstTopicOption = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("option:nth-child(2)")));
        firstTopicOption.click();

        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(), 'Добавить')]"));
        addButton.click();

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Добавить лабораторную работу')]"));
        addNewLabWorkButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Необходимо заполнить описание лабораторной работы", secondChildDiv.getText());
    }

    @Test
    @Order(5)
    public void topicsIsEmptyTest() {
        WebElement expandButton = driver.findElement(By.id("expand"));
        expandButton.click();

        WebElement addLabWorkButton = driver.findElement(By.id("add-labwork"));
        addLabWorkButton.click();

        WebElement nameInput = driver.findElement(By.id("labwork-name"));
        nameInput.sendKeys("Firefox LabWork");

        WebElement descriptionInput = driver.findElement(By.id("labwork-description"));
        descriptionInput.sendKeys("Описание новой лабораторной работы");

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Добавить лабораторную работу')]"));
        addNewLabWorkButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Необходимо добавить хотя бы одну тему", secondChildDiv.getText());
    }

}
