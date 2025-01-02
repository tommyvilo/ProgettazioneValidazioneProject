package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {

    @FindBy(xpath="//input[@id='username']")
    private WebElement usernameField;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public PageObject login(){
        usernameField.click();
        return new PageObject(driver);
    }


}
