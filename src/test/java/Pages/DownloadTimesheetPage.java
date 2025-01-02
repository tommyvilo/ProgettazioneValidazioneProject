package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class DownloadTimesheetPage extends PageObject {

    @FindBy(xpath="//a[@id='timesheet']")
    private List<WebElement> download;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public DownloadTimesheetPage(WebDriver driver) {
        super(driver);
    }

    public void downloadTimesheet(){
        download.get(0).click();
    }

    public LoginPage logout(){
        logoutButton.click();
        return new LoginPage(driver);
    }
}
