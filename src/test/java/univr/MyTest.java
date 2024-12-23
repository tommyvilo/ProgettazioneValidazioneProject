package univr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MyTest {

    protected WebDriver driver;
    @Before
    public void setUp() {
        WebDriverManager manager = WebDriverManager.edgedriver();
        if (driver == null)
            driver = manager.create();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void myTest() {
        driver.get("http://localhost:8080");
        driver.findElement(By.xpath("//a")).sendKeys(Keys.ENTER); //entering list page

        //ADDING A PERSON
        WebElement addButton = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a")));
        addButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[3]")));
        driver.findElement(By.xpath("//input[1]")).sendKeys("Tommaso" + Keys.ENTER);
        driver.findElement(By.xpath("//input[2]")).sendKeys("Vilotto" + Keys.ENTER);

        //VERIFING THAT THE PERSON HAS BEEN ADDED
        WebElement elementsDB = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//tbody/tr")));
        String[] readValue = elementsDB.getText().split(" ");
        System.out.println(readValue[2]);
        assertEquals("Tommaso", readValue[1]);
        assertEquals("Vilotto", readValue[2]);

        //CHANGING HIS USERNAME
        //elementsDB = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//tbody/tr")));
        elementsDB.findElements(By.xpath("//tbody/tr/td/a")).get(1).click();
        WebElement inputField = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[2]")));
        inputField.clear();
        inputField.sendKeys("Sturniolo" + Keys.ENTER);

        //VERIFING THE SURNAME HAS BEEN CHANGED
        elementsDB = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//tbody/tr")));
        readValue = elementsDB.getText().split(" ");
        System.out.println(readValue[2]);
        assertNotEquals("Vilotto", readValue[2]); //controllando che il cognome sia cambiato davvero

        //DELETING THE PERSON FROM DATABASE
        //elementsDB = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//tbody/tr")));
        elementsDB.findElements(By.xpath("//tbody/tr/td/a")).get(2).click(); //elimino il record

        //VERIFING THAT THE PERSON HAS BEEN DELETED SUCCESSFULLY
        List<WebElement> listaRecord = driver.findElements(By.xpath("//tbody/tr"));
        System.out.println(listaRecord.size());
        assertEquals(0, listaRecord.size()); //controllando che il record sia stato rimosso
    }

    }
