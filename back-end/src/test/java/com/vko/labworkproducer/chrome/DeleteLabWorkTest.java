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
public class DeleteLabWorkTest {

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
    public void successfulDeleteLabWorkTest() {
        WebElement labworkItem = driver.findElement(By.xpath("//h2[contains(text(), 'Chrome LabWork')]"));
        labworkItem.click();

        WebElement editLabworkButton = driver.findElement(By.id("edit-labwork"));
        editLabworkButton.click();

        WebElement addNewLabWorkButton = driver.findElement(By.xpath("//button[contains(text(), 'Удалить лабораторную работу')]"));
        addNewLabWorkButton.click();

        WebElement toastBody = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Toastify__toast-body")));

        List<WebElement> childDivs = toastBody.findElements(By.cssSelector("div"));
        WebElement secondChildDiv = childDivs.get(1);
        assertEquals("Лабораторная работа успешно удалена", secondChildDiv.getText());
    }

}
