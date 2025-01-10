package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ManageProjectPage extends PageObject {

    @FindBy(xpath="//a[@id='newProject']")
    private WebElement newProject;

    @FindBy(xpath="//tr[@id='project']")
    private List<WebElement> projects;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public ManageProjectPage(WebDriver driver) {
        super(driver);
    }

    public NewProjectPage newProject() {
        newProject.click();
        return new NewProjectPage(driver);
    }

    public int projectsNumber(){
        return projects.size();
    }

    public String getWelcomeString(){
        return welcomeTitle.getText();
    }


    public LoginPage logout(){
        logoutButton.click();
        return new LoginPage(driver);
    }
}
