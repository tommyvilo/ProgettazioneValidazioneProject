package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdministratorPage extends PageObject {

    @FindBy(xpath="//a[@id='manageUser']")
    private WebElement manageUser;

    @FindBy(xpath="//a[@id='manageProject']")
    private WebElement manageProject;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public AdministratorPage(WebDriver driver) {
        super(driver);
    }

    public ManageUsersPage manageUsers() {
        manageUser.click();
        return new ManageUsersPage(driver);
    }

    public ManageProjectPage manageProjects() {
        manageProject.click();
        return new ManageProjectPage(driver);
    }

    public AdministratorPage goTo(String url){
        driver.get(url);
        return new AdministratorPage(driver);
    }

    public LoginPage logout(){
        logoutButton.click();
        return new LoginPage(driver);
    }

    public String getWelcomeString(){
        return welcomeTitle.getText();
    }


}
