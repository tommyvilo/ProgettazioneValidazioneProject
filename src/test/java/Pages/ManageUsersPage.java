package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ManageUsersPage extends PageObject {

    @FindBy(xpath="//a[@id='newUser']")
    private WebElement manageUser;

    @FindBy(xpath="//a[@id='deleteUser']")
    private List<WebElement> deleteUser;

    @FindBy(xpath="//td[@id='usernameUser']")
    private List<WebElement> usernameUserList;

    @FindBy(xpath="//h1[@id='welcomeTitle']")
    private WebElement welcomeTitle;

    @FindBy(xpath="//a[@id='logout']")
    private WebElement logoutButton;

    public ManageUsersPage(WebDriver driver) {
        super(driver);
    }

    public NewUserPage newUser() {
        wait.until(ExpectedConditions.elementToBeClickable(manageUser));
        manageUser.click();
        return new NewUserPage(driver);
    }

    public int usersNumber(){
        return deleteUser.size();
    }

    public void deleteUser(int index) {
        wait.until(ExpectedConditions.elementToBeClickable(deleteUser.get(index)));
        deleteUser.get(index).click();
    }

    public void deleteUser(String username) {
        int index = 0;
        wait.until(ExpectedConditions.visibilityOfAllElements(usernameUserList));
        for(WebElement user : usernameUserList) {
            if(user.getText().equals(username)) {
                break;
            }
            index++;
        }
        deleteUser(index);
    }

    public String getWelcomeString(){
        wait.until(ExpectedConditions.visibilityOf(welcomeTitle));
        return welcomeTitle.getText();
    }


    public LoginPage logout(){
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
        return new LoginPage(driver);
    }

}
