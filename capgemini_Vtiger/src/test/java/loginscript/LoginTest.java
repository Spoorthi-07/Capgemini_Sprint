package loginscript;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.FileUtility;
import objrepo.LoginPage;



public class LoginTest extends BaseClass {

    @Test
    public void verifyValidLogin() throws Exception {

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(
            "http://49.249.29.4:8888"
        );

        LoginPage l = new LoginPage(driver);
        FileUtility fu = new FileUtility();
        l.login(
                fu.getDataFromPropertyFile("username"),
                fu.getDataFromPropertyFile("password")
            );
        // Add an assertion for the expected page here.

        driver.quit();
    }
    
    @Test
    public void verifyInvaildUsername() throws Exception {

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(
            "http://49.249.29.4:8888"
        );

        LoginPage l = new LoginPage(driver);
        FileUtility fu = new FileUtility();
        l.login("Admin",fu.getDataFromPropertyFile("password"));
        // Add an assertion for the expected page here.
        Assert.assertFalse(
                l.getErrorMessage().contains(
                    "Username and password do not match"
                ),
                "You must specify a valid username and password."
            );
        driver.quit();
    }
}