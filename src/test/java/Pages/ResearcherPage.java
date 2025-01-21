package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ResearcherPage extends PageObject {

    @FindBy(xpath="//input[@id='hours']")
    private List<WebElement> sliderList;

    @FindBy(xpath="//td[@id='projectTitle']")
    private List<WebElement> titleList;

    @FindBy(xpath="//a[@id='downloadTimesheet']")
    private List<WebElement> download;

    @FindBy(xpath="//button[@id='saveButton']")
    private WebElement saveButton;

    @FindBy(xpath="//input[@id='vacationCheckbox']")
    private WebElement vacationCheckbox;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    @FindBy(xpath="//input[@name='selectedDate']")
    private WebElement datePicker;

    @FindBy(xpath="//button[@id='saveButtonDate']")
    private WebElement loadPageButton;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public ResearcherPage(WebDriver driver) {
        super(driver);
    }

    public void signHours(int index){
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(sliderList.get(index)));
        actions.dragAndDropBy(sliderList.get(index), 100, 0).perform();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }

    public ResearcherPage setDate(String date){
        wait.until(ExpectedConditions.visibilityOf(datePicker));
        datePicker.sendKeys(date);
        loadPageButton.click();
        //welcomeTitle.click();
        return new ResearcherPage(driver);
    }

    public String getProjectTitle(int index){
        wait.until(ExpectedConditions.visibilityOf(sliderList.get(index)));
        return titleList.get(index).getText();
    }

    public DownloadTimesheetPage downloadTimesheet(int index){
        wait.until(ExpectedConditions.elementToBeClickable(download.get(index)));
        download.get(index).click();
        return new DownloadTimesheetPage(driver);
    }

    public DownloadTimesheetPage downloadTimesheet(String projectTitle){
        wait.until(ExpectedConditions.visibilityOfAllElements(titleList));
        int index = 0;
        for(WebElement titleProject : titleList){
            if(projectTitle.equals(titleProject.getText())){
                wait.until(ExpectedConditions.elementToBeClickable(download.get(index)));
                download.get(index).click();
                return new DownloadTimesheetPage(driver);
            }
            index ++;
        }
        wait.until(ExpectedConditions.elementToBeClickable(download.get(index)));
        download.get(index).click();
        return new DownloadTimesheetPage(driver);
    }

    public void signVacation(){
        wait.until(ExpectedConditions.elementToBeClickable(vacationCheckbox));
        vacationCheckbox.click();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }

    public boolean sliderStatus(){
        wait.until(ExpectedConditions.visibilityOf(sliderList.get(0)));
        return sliderList.get(0).isEnabled();
    }

    public String getWelcomeString(){
        wait.until(ExpectedConditions.visibilityOf(welcomeTitle));
        return welcomeTitle.getText();
    }

    public boolean isThereProject(String projectName){
        wait.until(ExpectedConditions.visibilityOfAllElements(titleList));
        for(WebElement titleProject : titleList){
            if(titleProject.getText().equals(projectName)){
                return true;
            }
        }
        return false;
    }

    public ResearcherPage goTo(String url){
        driver.get(url);
        return new ResearcherPage(driver);
    }

    public LoginPage logout(){
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
        return new LoginPage(driver);
    }

}
