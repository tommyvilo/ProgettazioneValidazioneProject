package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ManageUsersPage extends PageObject {

    @FindBy(xpath="//a[@id='newUser']")
    private WebElement manageUser;

    @FindBy(xpath="//a[@id='deleteUser']")
    private List<WebElement> deleteUser;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public ManageUsersPage(WebDriver driver) {
        super(driver);
    }

    public NewUserPage newUser() {
        manageUser.click();
        return new NewUserPage(driver);
    }

    public int usersNumber(){
        return deleteUser.size();
    }

    public void deleteUser(int index) {
        deleteUser.get(index).click();
    }

    public LoginPage logout(){
        logoutButton.click();
        return new LoginPage(driver);
    }

}
