package Pages;

import org.hibernate.annotations.processing.Find;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NewUserPage extends PageObject {

    @FindBy(xpath="//input[@id='username']")
    private WebElement username;

    @FindBy(xpath="//input[@id='password']")
    private WebElement password;

    @FindBy(xpath="//input[@id='name']")
    private WebElement name;

    @FindBy(xpath="//input[@id='surname']")
    private WebElement surname;

    @FindBy(xpath="//input[@id='cf']")
    private WebElement cf;

    @FindBy(xpath="//select[@id='userType']")
    private WebElement userType;

    @FindBy(xpath="//button[@id='submit']")
    private WebElement submit;

    public NewUserPage(WebDriver driver) {
        super(driver);
    }

    public ManageUsersPage createUser(String username, String password, String name, String surname, String cf, String userType){
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.name.sendKeys(name);
        this.surname.sendKeys(surname);
        this.cf.sendKeys(cf);
        this.userType.sendKeys(userType);
        submit.click();
        return new ManageUsersPage(driver);
    }
}
