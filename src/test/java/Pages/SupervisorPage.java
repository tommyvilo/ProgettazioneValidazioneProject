package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SupervisorPage extends PageObject {

    @FindBy(xpath="//a[@id='supervisorAction']")
    private WebElement supervisorActionButton;

    @FindBy(xpath="//input[@id='hours']")
    private List<WebElement> sliderList;

    @FindBy(xpath="//td[@id='projectTitle']")
    private List<WebElement> titleList;

    @FindBy(xpath="//a[@id='downloadTimesheet']")
    private List<WebElement> download;

    @FindBy(xpath="//button[@id='saveButton']")
    private WebElement saveButton;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    @FindBy(xpath="//input[@name='selectedDate']")
    private WebElement datePicker;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;


    public SupervisorPage(WebDriver driver) {
        super(driver);
    }

    public SupervisorActionPage goToSupervisorAction() {
        wait.until(ExpectedConditions.elementToBeClickable(supervisorActionButton));
        supervisorActionButton.click();
        return new SupervisorActionPage(driver);
    }

    public void signHours(int index){
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(sliderList.get(index)));
        actions.dragAndDropBy(sliderList.get(index), 100, 0).perform();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }

    public String getProjectTitle(int index){
        wait.until(ExpectedConditions.visibilityOf(titleList.get(index)));
        return titleList.get(index).getText();
    }

    public DownloadTimesheetPage downloadTimesheet(int index){
        wait.until(ExpectedConditions.elementToBeClickable(download.get(index)));
        download.get(index).click();
        return new DownloadTimesheetPage(driver);
    }

    public String getWelcomeString(){
        wait.until(ExpectedConditions.visibilityOf(welcomeTitle));
        return welcomeTitle.getText();
    }

    public SupervisorPage goTo(String url){
        driver.get(url);
        return new SupervisorPage(driver);
    }

    public SupervisorPage setDate(String date){
        wait.until(ExpectedConditions.visibilityOf(datePicker));
        datePicker.sendKeys(date);
        welcomeTitle.click();
        return new SupervisorPage(driver);
    }

    public LoginPage logout(){
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
        return new LoginPage(driver);
    }

}
