package objrepo;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrganizationPage {

    WebDriver driver;
    WebDriverWait wait;

    public OrganizationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );
        PageFactory.initElements(driver, this);
    }

    // Click Organizations tab
    public void clickOrganizationsTab() {

        By organizationsTab = By.xpath(
            "//a[normalize-space()='ORGANIZATIONS' " +
            "or normalize-space()='Organizations']"
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(organizationsTab)
        ).click();
    }

    // Verify Organizations page
    public boolean isOrganizationPageDisplayed() {

        By heading = By.xpath(
            "//*[normalize-space()='Organizations']"
        );

        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(heading)
            ).isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    // Click Create Organization button
    public void clickCreateOrganization() {

        By createButton = By.xpath(
            "//img[contains(@title,'Create Organization')]" +
            " | //input[contains(@title,'Create Organization')]" +
            " | //a[contains(@href,'action=EditView') " +
            "and contains(@href,'Accounts')]"
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(createButton)
        ).click();
    }

    // Enter Organization Name
    public void enterOrganizationName(String name) {

        By organizationNameField = By.name("accountname");

        WebElement field = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                organizationNameField
            )
        );

        field.clear();
        field.sendKeys(name);
    }

    // Click Save button
    public void clickSaveOrganization() {

        By saveButton = By.xpath(
            "//input[@type='submit' and " +
            "contains(translate(@value,'SAVE','save'),'save')]" +
            " | //input[@type='button' and " +
            "contains(translate(@value,'SAVE','save'),'save')]" +
            " | //button[contains(normalize-space(.),'Save')]" +
            " | //button[@title='Save']" +
            " | //input[@name='button' and contains(@value,'Save')]"
        );

        WebElement button = wait.until(
            ExpectedConditions.elementToBeClickable(saveButton)
        );

        button.click();
    }

    // Check whether a browser alert is present
    public boolean isAlertPresent() {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.alertIsPresent());

            return true;

        } catch (TimeoutException e) {
            return false;
        }
    }

    // Get browser alert message
    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    // Accept browser alert
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    // Verify the created organization in the list

	public boolean isOrganizationDisplayed(String organizationName) {
	
	    try {
	        return wait.until(
	            ExpectedConditions.textToBePresentInElementLocated(
	                By.tagName("body"),
	                organizationName
	            )
	        );
	
	    } catch (TimeoutException e) {
	        System.out.println("Organization not found on current page: "
	                + organizationName);
	        System.out.println("Current URL: " + driver.getCurrentUrl());
	        System.out.println("Page title: " + driver.getTitle());
	        return false;
	    }
	}
	

	public void clickSaveWithoutOrganizationName() {
	    clickSaveOrganization();
	}
	

	public String getOrganizationNameValidationMessage() {
	    return driver.switchTo().alert().getText();
	}
	


}