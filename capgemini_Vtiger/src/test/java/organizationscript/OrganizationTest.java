package organizationscript;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.ExcelUtility;
import objrepo.OrganizationPage;

public class OrganizationTest extends BaseClass {

    ExcelUtility eu = new ExcelUtility();

    @Test(groups = {"Smoke", "Positive"})
    public void verifyOrganizationModuleOpens() {

        OrganizationPage organization = new OrganizationPage(driver);

        // Open Organizations module
        organization.clickOrganizationsTab();

        // Verify module is displayed
        Assert.assertTrue(
            organization.isOrganizationPageDisplayed(),
            "FAIL: Organizations module did not open"
        );

        Reporter.log(
            "PASS: Organizations module opened successfully",
            true
        );
    }

    @Test(groups = {"Functional", "Positive"})
    public void createOrganizationWithMandatoryFieldsOnly()
            throws Exception {

        OrganizationPage organization = new OrganizationPage(driver);

        // Step 1: Open Organizations module
        organization.clickOrganizationsTab();

        // Step 2: Open Create Organization form
        organization.clickCreateOrganization();

        // Step 3: Read organization name from Excel
        String baseName = eu.getDataFromExcel("Organization", 1, 0);

        // Step 4: Generate unique name for every execution
        String organizationName =
                baseName + "_" + System.currentTimeMillis();

        // Step 5: Enter mandatory field
        organization.enterOrganizationName(organizationName);

        // Step 6: Save organization
        organization.clickSaveOrganization();
        
//        System.out.println("URL after Save: " + driver.getCurrentUrl());
//        System.out.println("Title after Save: " + driver.getTitle());
//        System.out.println("Page text after Save: "
//                + driver.findElement(org.openqa.selenium.By.tagName("body"))
//                       .getText());

        // Step 7: Handle any alert from the application
        if (organization.isAlertPresent()) {

            String alertMessage = organization.getAlertText();
            organization.acceptAlert();

            Assert.fail(
                "Organization creation failed. Alert: " + alertMessage
            );
        }

        // Step 8: Verify organization creation
        Assert.assertTrue(
            organization.isOrganizationDisplayed(organizationName),
            "Organization was not created: " + organizationName
        );

        Reporter.log(
            "PASS: Organization created successfully: "
                    + organizationName,
            true
        );
    }
    


	@Test(groups = {"Functional", "Negative"})
	public void verifyOrganizationCannotBeCreatedWithoutName() {
	
	    OrganizationPage organization = new OrganizationPage(driver);
	
	    // Open Organizations module
	    organization.clickOrganizationsTab();
	
	    // Open Create Organization form
	    organization.clickCreateOrganization();
	
	    // Leave Organization Name empty
	    organization.enterOrganizationName("");
	
	    // Click Save
	    organization.clickSaveWithoutOrganizationName();
	
	    // Capture the alert text
	    String actualMessage = organization.getOrganizationNameValidationMessage();
	
	    // Verify the expected validation message
	    Assert.assertEquals(
	        actualMessage,
	        "Organization Name cannot be empty",
	        "Unexpected validation message"
	    );
	
	    // Close the alert
	    organization.acceptAlert();
	
	    Reporter.log(
	        "PASS: Empty Organization Name was rejected successfully",
	        true
	    );
	}
	

	


}