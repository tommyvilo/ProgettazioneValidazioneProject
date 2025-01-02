package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SupervisorPage extends PageObject {

    @FindBy(xpath="//a[@id='manageResearcher']")
    private List<WebElement> manageResearchers;

    @FindBy(xpath="//a[@id='validationTimesheet']")
    private List<WebElement> validationTimesheets;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public SupervisorPage(WebDriver driver) {
        super(driver);
    }

    public SuperviseProjectPage manageResearcher(){
        manageResearchers.get(0).click();
        return new SuperviseProjectPage(driver);
    }

    public ValidationTimesheetPage manageValidation(){
        validationTimesheets.get(0).click();
        return new ValidationTimesheetPage(driver);
    }

    public LoginPage logout(){
        logoutButton.click();
        return new LoginPage(driver);
    }

}
