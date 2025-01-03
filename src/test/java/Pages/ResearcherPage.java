package Pages;

import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
import it.univr.Repository.UserRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ResearcherPage extends PageObject {

    @FindBy(xpath="//input[@id='hours'][1]")
    private WebElement firstSlider;

    @FindBy(xpath="//td[@id='projectTitle'][1]")
    private WebElement nameFirstSlider;

    @FindBy(xpath="//a[@id='downloadTimesheet']")
    private List<WebElement> download;

    @FindBy(xpath="//button[@id='saveButton']")
    private WebElement saveButton;

    @FindBy(xpath="//input[@id='vacationCheckbox']")
    private WebElement vacationCheckbox;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    private String sliderValue;

    public ResearcherPage(WebDriver driver) {
        super(driver);
    }

    public void signHours(){
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(firstSlider, 100, 0).perform();
        saveButton.click();
    }

    public String getProjectTitle(){
        return nameFirstSlider.getText();
    }

    public DownloadTimesheetPage downloadTimesheet(){
        download.get(2).click();
        return new DownloadTimesheetPage(driver);
    }

    public DownloadTimesheetPage downloadTimesheet(int index){
        download.get(index).click();
        return new DownloadTimesheetPage(driver);
    }

    public void signVacation(){
        vacationCheckbox.click();
        saveButton.click();
    }

    public boolean sliderStatus(){
        return firstSlider.isEnabled();
    }

    public String getWelcomeString(){
        return welcomeTitle.getText();
    }

    public LoginPage logout(){
        logoutButton.click();
        return new LoginPage(driver);
    }

}
