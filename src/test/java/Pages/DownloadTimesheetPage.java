package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DownloadTimesheetPage extends PageObject {

    @FindBy(xpath="//a[@id='timesheet']")
    private List<WebElement> download;

    @FindBy(xpath="//td[@id='monthYear']")
    private List<WebElement> monthYears;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public DownloadTimesheetPage(WebDriver driver) {
        super(driver);
    }

    public void downloadTimesheet(){
        download.get(0).click();
    }

    public boolean downloadTimesheet(String monthYear){
        int index = 0;
        for(WebElement date : this.monthYears){
            if(date.getText().equals(monthYear)){
                download.get(index).click();
                return true;
            }
            index++;
        }
        return false;
    }

    public String getWelcomeString(){
        return welcomeTitle.getText();
    }


    public LoginPage logout(){
        logoutButton.click();
        return new LoginPage(driver);
    }
}
