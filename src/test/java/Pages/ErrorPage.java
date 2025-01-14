package Pages;

import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
import it.univr.Repository.UserRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ErrorPage extends PageObject {


    @FindBy(xpath="//p[@id='errorMessage']")
    private WebElement usernameField;

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessage(){
        return usernameField.getText();
    }


}
