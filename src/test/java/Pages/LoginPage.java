package Pages;

import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
import it.univr.Repository.UserRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {


    @FindBy(xpath="//input[@id='username']")
    private WebElement usernameField;
    @FindBy(xpath="//input[@id='password']")
    private WebElement passwordField;
    @FindBy(xpath="//button")
    private WebElement button;
    @FindBy(xpath="//h1[@id='loginTitle']")
    private WebElement loginTitle;
    @FindBy(xpath="//div[@id='errorMessage']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public PageObject login(String username, String password, UserRepository userRepository){
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        button.click();

        if(!userRepository.findByUsername(username).getPassword().equals(password)){
            return new LoginPage(driver);
        }
        if(userRepository.findByUsername(username) instanceof Researcher){
            return new ResearcherPage(driver);
        }
        else if(userRepository.findByUsername(username) instanceof Supervisor){
            return new SupervisorPage(driver);
        } else {
            return new AdministratorPage(driver);
        }
    }

    public String getLoginTitle(){
        return loginTitle.getText();
    }

    public LoginPage goTo(String url){
        driver.get(url);
        return new LoginPage(driver);
    }

    public String getErrorMessage(){
        return errorMessage.getText();
    }
}
