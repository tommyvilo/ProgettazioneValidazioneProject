package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewProjectPage extends PageObject {

    @FindBy(xpath="//input[@id='title']")
    private WebElement title;

    @FindBy(xpath="//input[@id='cup']")
    private WebElement cup;

    @FindBy(xpath="//input[@id='code']")
    private WebElement code;

    @FindBy(xpath="//input[@id='denominazioneSoggetto']")
    private WebElement denominazioneSoggetto;

    @FindBy(xpath="//input[@id='cfSoggetto']")
    private WebElement cfSoggetto;

    @FindBy(xpath="//select[@id='supervisor']")
    private WebElement supervisor;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    @FindBy(xpath="//button[@id='submit']")
    private WebElement submit;

    public NewProjectPage(WebDriver driver) {
        super(driver);
    }

    public ManageProjectPage createProject(String title, String cup, String code, String denominazioneSoggetto,String cfSoggetto, String supervisor){
        wait.until(ExpectedConditions.elementToBeClickable(submit));
        this.title.sendKeys(title);
        this.cup.sendKeys(cup);
        this.code.sendKeys(code);
        this.denominazioneSoggetto.sendKeys(denominazioneSoggetto);
        this.cfSoggetto.sendKeys(cfSoggetto);
        this.supervisor.sendKeys(supervisor);
        this.submit.click();
        return new ManageProjectPage(driver);
    }

    public String getWelcomeString(){
        wait.until(ExpectedConditions.visibilityOf(welcomeTitle));
        return welcomeTitle.getText();
    }

}
