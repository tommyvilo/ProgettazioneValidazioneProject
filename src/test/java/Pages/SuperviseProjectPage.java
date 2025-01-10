package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SuperviseProjectPage extends PageObject {

    @FindBy(xpath="//input[@id='checkBox']")
    private List<WebElement> researchers;

    @FindBy(xpath="//button[@id='save-button']")
    private WebElement saveButton;

    @FindBy(xpath="//input[@id='projectId']")
    private WebElement projectId;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public SuperviseProjectPage(WebDriver driver) {
        super(driver);
    }

    public void addResearcher(int index){
        researchers.get(index).click();
        saveButton.click();
    }

    public int getResearcherSelected(){
        int counter = 0;
        for (WebElement w: researchers){
            if (w.isSelected()){
                counter++;
            }
        }
        return counter;
    }

    public String getProjectId(){
        return projectId.getAttribute("value");
    }

    public String getWelcomeString(){
        return welcomeTitle.getText();
    }


    public LoginPage logout(){
        logoutButton.click();
        return new LoginPage(driver);
    }
}
