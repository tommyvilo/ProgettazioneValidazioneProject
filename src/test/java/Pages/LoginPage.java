package Pages;

import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
import it.univr.Repository.UserRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginPage extends PageObject {


    @FindBy(xpath="//input[@id='username']")
    private WebElement usernameField;
    @FindBy(xpath="//input[@id='password']")
    private WebElement passwordField;
    @FindBy(xpath="//button")
    private WebElement button;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public PageObject login(String username, String password, UserRepository userRepository){
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);

        button.click();

        if(userRepository.findByUsername(username) instanceof Researcher){
            return new ResearcherPage(driver);
        }
        else if(userRepository.findByUsername(username) instanceof Supervisor){
            return new SupervisorPage(driver);
        } else {
            return new AdministratorPage(driver);
        }
    }
}
