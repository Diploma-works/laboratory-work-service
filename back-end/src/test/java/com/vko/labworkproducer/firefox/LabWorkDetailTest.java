package com.vko.labworkproducer.firefox;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LabWorkDetailTest {

    static WebDriver driver;

    @BeforeAll
    static void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("http://localhost:3000/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    public void checkLabWorkDetailTest() {
        List<WebElement> labworkItems = driver.findElements(By.className("labwork-item"));
        labworkItems.get(0).click();

        WebElement usernameElement = driver.findElement(By.className("labwork-name"));
        String usernameText = usernameElement.getText();

        WebElement descriptionElement = driver.findElement(By.tagName("p"));
        String descriptionText = descriptionElement.getText();

        List<WebElement> topicItems = driver.findElements(By.tagName("li"));

        assertFalse(usernameText.isEmpty());
        assertFalse(descriptionText.isEmpty());
        assertFalse(topicItems.isEmpty());
    }

}
