package ru.lanit.webdriver;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.github.bonigarcia.wdm.config.DriverManagerType.CHROME;

public class AvitoTest {
    static WebDriver webDriver;
    static WebDriverWait driverWait;

    @BeforeTest
    public void setUp() {
        WebDriverManager.getInstance(CHROME).setup();
        webDriver = new ChromeDriver();
        driverWait = new WebDriverWait(webDriver, 5);
        webDriver.get("https://www.avito.ru/");
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        webDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
//        webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    @Test
    public void getPrinter() {
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='category']/option[@value=99]")));
        webDriver.findElement(By.xpath("//select[@id='category']/option[@value=99]")).click();

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search']")));
        webDriver.findElement(By.xpath("//input[@id='search']")).sendKeys("Принтер");

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-marker='search-form/region']")));
        webDriver.findElement(By.xpath("//div[@data-marker='search-form/region']")).click();

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-marker='popup-location/region/input']")));
        webDriver.findElement(By.xpath("//input[@data-marker='popup-location/region/input']"))
                .sendKeys("Владивосток");
        //Временное решение
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@data-marker='suggest(0)']")));
        webDriver.findElement(By.xpath("//li[@data-marker='suggest(0)']")).click();

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-marker='popup-location/save-button']")));
        webDriver.findElement(By.xpath("//button[@data-marker='popup-location/save-button']")).click();

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@data-marker='delivery-filter']")));
        WebElement elementDelivery = webDriver.findElement(By.xpath("//label[@data-marker='delivery-filter']"));
        if (!elementDelivery.isSelected()) {
            elementDelivery.click();
        }

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-marker='search-filters/submit-button']")));
        webDriver.findElement(By.xpath("//button[@data-marker='search-filters/submit-button']")).click();

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[text()='Дороже']")));
        webDriver.findElement(By.xpath("//option[text()='Дороже']")).click();

        List<WebElement> title = webDriver.findElements(By.xpath("//div[@class='snippet-title-row']"));
        List<WebElement> price = webDriver.findElements(By.xpath("//div[@class='snippet-price-row']"));

        for (int i = 0; i < 3; i++) {
            System.out.println("Принтер №" + (i + 1));
            System.out.println(title.get(i).findElement(By.xpath("./h3/a")).getText());
            System.out.println(price.get(i).findElement(By.xpath("./span[@class='snippet-price ']")).getText());
            System.out.println("-------------------------");
        }
    }


    @AfterTest
    public void tearDown() {
        webDriver.quit();
    }
}
