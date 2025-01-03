package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ValidationTimesheetPage extends PageObject {

    @FindBy(xpath="//a[@id='validateLink']")
    private List<WebElement> validateLink;

    @FindBy(xpath="//td[@id='status']")
    private List<WebElement> status;

    @FindBy(xpath="//td[@id='monthYear']")
    private List<WebElement> monthYears;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public ValidationTimesheetPage(WebDriver driver) {
        super(driver);
    }

    public void validateTimesheet(int index){
        validateLink.get(index).click();
    }

    public void validateTimesheet(String monthYear){
        int index = 0;
        for(WebElement data : this.monthYears){
            if(data.getText().equals(monthYear)){
                break;
            }
            index++;
        }
        System.out.println(index);
        validateLink.get(index).click();
    }

    public String getStatusTimesheet(String monthYear){
        int index = 0;
        for(WebElement data : this.monthYears){
            if(data.getText().equals(monthYear)){
                break;
            }
            index++;
        }
        System.out.println(index);
        return status.get(index).getText();
    }

    public String getStatusTimesheet(int index){
        return status.get(index).getText();
    }

    public LoginPage logout(){
        logoutButton.click();
        return new LoginPage(driver);
    }
}
