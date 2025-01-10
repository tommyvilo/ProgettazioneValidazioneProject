package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SupervisorActionPage extends PageObject {

    @FindBy(xpath="//a[@id='manageResearcher']")
    private List<WebElement> manageResearchers;

    @FindBy(xpath="//a[@id='validationTimesheet']")
    private List<WebElement> validationTimesheets;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    public SupervisorActionPage(WebDriver driver) {
        super(driver);
    }

    public SuperviseProjectPage manageResearcher(int index){
        manageResearchers.get(index).click();
        return new SuperviseProjectPage(driver);
    }

    public ValidationTimesheetPage manageValidation(){
        validationTimesheets.get(0).click();
        return new ValidationTimesheetPage(driver);
    }

    public String getWelcomeString(){
        return welcomeTitle.getText();
    }


}
