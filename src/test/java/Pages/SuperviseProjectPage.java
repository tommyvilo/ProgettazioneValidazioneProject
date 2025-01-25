package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    public void selectAndSaveResearcher(int index){
        wait.until(ExpectedConditions.elementToBeClickable(researchers.get(index)));
        researchers.get(index).click();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }

    public int getResearcherSelected(){
        int counter = 0;
        wait.until(ExpectedConditions.visibilityOfAllElements(researchers));
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
        wait.until(ExpectedConditions.visibilityOf(welcomeTitle));
        return welcomeTitle.getText();
    }

    public LoginPage logout(){
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
        return new LoginPage(driver);
    }
}
