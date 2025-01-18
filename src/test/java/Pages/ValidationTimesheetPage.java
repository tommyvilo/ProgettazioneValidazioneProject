package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ValidationTimesheetPage extends PageObject {

    @FindBy(xpath="//a[@id='validateLink']")
    private List<WebElement> validateLink;

    @FindBy(xpath="//td[@id='status']")
    private List<WebElement> status;

    @FindBy(xpath="//td[@id='download']")
    private List<WebElement> download;

    @FindBy(xpath="//td[@id='monthYear']")
    private List<WebElement> monthYears;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public ValidationTimesheetPage(WebDriver driver) {
        super(driver);
    }

    public void validateTimesheet(int index){
        wait.until(ExpectedConditions.elementToBeClickable(validateLink.get(index)));
        validateLink.get(index).click();
    }

    public void validateTimesheet(String monthYear){
        int index = 0;
        wait.until(ExpectedConditions.visibilityOfAllElements(this.monthYears));
        for(WebElement data : this.monthYears){
            if(data.getText().equals(monthYear)){
                break;
            }
            index++;
        }
        wait.until(ExpectedConditions.elementToBeClickable(validateLink.get(index)));
        validateLink.get(index).click();
    }

    public void downloadTimesheet(int index){
        wait.until(ExpectedConditions.elementToBeClickable(download.get(index)));
        download.get(index).click();
    }

    public String getStatusTimesheet(String monthYear){
        int index = 0;
        wait.until(ExpectedConditions.visibilityOfAllElements(this.monthYears));
        for(WebElement data : this.monthYears){
            if(data.getText().equals(monthYear)){
                break;
            }
            index++;
        }
        wait.until(ExpectedConditions.visibilityOf(status.get(index)));
        return status.get(index).getText();
    }

    public String getStatusTimesheet(int index){
        wait.until(ExpectedConditions.visibilityOf(status.get(index)));
        return status.get(index).getText();
    }

    public String getWelcomeString(){
        wait.until(ExpectedConditions.visibilityOf(welcomeTitle));
        return welcomeTitle.getText();
    }

    public LoginPage logout(){
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
        return new LoginPage(driver);
    }
}
