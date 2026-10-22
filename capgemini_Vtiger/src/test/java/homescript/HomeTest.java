package homescript;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import genericutility.BaseClass;
import genericutility.ExcelUtility;
import objrepo.HomePage;

public class HomeTest extends BaseClass {
		
	@Test(groups = {"Functional", "Positive"})
	public void verifyAddDashboardWidget() throws Exception {

	    ExcelUtility eu = new ExcelUtility();
	    HomePage home = new HomePage(driver);

	    home.clickAddWidget();
	    home.clickDashboardOption();

	    String windowTitle =
	            eu.getDataFromExcel("HomeWidget", 3, 0);

	    home.addWindowTitle(windowTitle);
	    home.selectdashnameByIndex(0);
	    home.selectdashtypeByIndex(1);
	    home.clickSaveBtn();

	    WebDriverWait wait =
	            new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Verify that the popup closes after saving
	    Assert.assertTrue(
	            wait.until(ExpectedConditions.invisibilityOfElementLocated(
	                    By.id("stufftitle_id")
	            )),
	            "FAIL: Add Dashboard popup did not close after saving"
	    );
	}
	
	@Test(groups = {"Functional", "Negative"})
	public void verifyAddDashboardWidgetWithoutTitle() throws Exception {

	    HomePage home = new HomePage(driver);

	    home.clickAddWidget();
	    home.clickDashboardOption();

	    // Leave Window Title empty
	    home.selectdashnameByIndex(0);
	    home.selectdashtypeByIndex(1);

	    home.clickSaveBtn();

	    WebDriverWait wait =
	            new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Capture the validation alert
	    Alert alert = wait.until(
	            ExpectedConditions.alertIsPresent()
	    );

	    String actualMessage = alert.getText();
	    Reporter.log("Error Message: " + actualMessage, true);


	    // Verify the expected validation message
	    Assert.assertEquals(actualMessage,"Please enter Window Title.",
	            "FAIL: Incorrect validation message"
	    );
	    
	    // Close the alert
	    alert.accept();
	}
	
	@Test
    public void createNotebookFromExcel() throws IOException {

        HomePage home = new HomePage(driver);
     
        ExcelUtility eu = new ExcelUtility();

        String title =
                eu.getDataFromExcel("NotebookTitles", 1, 1);

        Assert.assertNotNull(title, "Notebook title is null");
        Assert.assertFalse(title.trim().isEmpty(),
                "Notebook title is empty");

        home.clickAddWidget();
        home.clickNotebookOption();

        Assert.assertTrue(home.isWindowTitleDisplayed(),
                "Add Notebook popup was not opened");

        home.addWindowTitle(title);
        home.clickSaveBtn();
    }
	
}