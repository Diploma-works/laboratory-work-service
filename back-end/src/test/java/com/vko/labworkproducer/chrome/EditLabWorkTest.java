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
public class EditLabWorkTest {

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
    public void successfulEditLabWorkTest() {
        WebElement labworkItem = driver.findElement(By.xpath("//h2[contains(text(), 'Chrome LabWork')]"));
        labworkItem.click();

        WebElement editLabworkButton = driver.findElement(By.id("edit-labwork"));
        editLabworkButton.click();

        WebElement nameInput = driver.findElement(By.id("labwork-name"));
        nameInput.clear();
        nameInput.sendKeys("Chrome LabWork1");

        WebElement descriptionInput = driver.findElement(By.id("labwork-description"));
        descriptionInput.clear();
        descriptionInput.sendKeys("Описание новой лабораторной работы1");

        WebElement topicSelect = driver.findElement(By.cssSelector("select"));
        topicSelect.click();

        WebElement firstTopicOption = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("option:nth-child(2)")));
        firstTopicOption.click();

        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(), 'Добавить')]"));
        addButton.click();

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Сохранить')]"));
        addNewLabWorkButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Лабораторная работа успешно сохранена", secondChildDiv.getText());
    }

    @Test
    @Order(2)
    public void labWorkNameAlreadyExistsTest() {
        WebElement labworkItem = driver.findElement(By.xpath("//h2[contains(text(), 'Chrome LabWork')]"));
        labworkItem.click();

        WebElement editLabworkButton = driver.findElement(By.id("edit-labwork"));
        editLabworkButton.click();

        WebElement nameInput = driver.findElement(By.id("labwork-name"));
        nameInput.clear();
        nameInput.sendKeys("Лабораторная работа №1");

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Сохранить')]"));
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
        WebElement labworkItem = driver.findElement(By.xpath("//h2[contains(text(), 'Chrome LabWork')]"));
        labworkItem.click();

        WebElement editLabworkButton = driver.findElement(By.id("edit-labwork"));
        editLabworkButton.click();

        WebElement nameInput = driver.findElement(By.id("labwork-name"));
        nameInput.clear();
        nameInput.sendKeys(" ");

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Сохранить')]"));
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
        WebElement labworkItem = driver.findElement(By.xpath("//h2[contains(text(), 'Chrome LabWork')]"));
        labworkItem.click();

        WebElement editLabworkButton = driver.findElement(By.id("edit-labwork"));
        editLabworkButton.click();

        WebElement nameInput = driver.findElement(By.id("labwork-description"));
        nameInput.clear();
        nameInput.sendKeys(" ");

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Сохранить')]"));
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
        WebElement labworkItem = driver.findElement(By.xpath("//h2[contains(text(), 'Chrome LabWork')]"));
        labworkItem.click();

        WebElement editLabworkButton = driver.findElement(By.id("edit-labwork"));
        editLabworkButton.click();

        List<WebElement> labworkItems = driver.findElements(By.className("delete-topic-button"));
        for (int i = labworkItems.size() - 1; i >= 0; i--) {
            labworkItems.get(i).click();
        }

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Сохранить')]"));
        addNewLabWorkButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Необходимо добавить хотя бы одну тему", secondChildDiv.getText());
    }

}
