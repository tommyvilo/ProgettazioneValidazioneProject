package Pages;

import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
import it.univr.Repository.UserRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ErrorPage extends PageObject {

    @FindBy(xpath="//p[@id='errorMessage']")
    private WebElement usernameField;

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessage(){
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        return usernameField.getText();
    }


}
