//
//package contactscript;
//
//import java.io.IOException;
//
//import org.testng.Assert;
//import org.testng.Reporter;
//import org.testng.annotations.Test;
//
//import genericutility.BaseClass;
//import genericutility.ExcelUtility;
//import objrepo.ContactPage;
//
//public class ContactTest extends BaseClass {
//
//    ExcelUtility eu = new ExcelUtility();
//    
//    // Handle alerts after saving
//    private void failIfAlertPresent(ContactPage contact) {
//        if (contact.isAlertPresent()) {
//            String message = contact.getAlertText();
//            contact.acceptAlert();
//
//            Assert.fail("Application validation alert: " + message);
//        }
//    }
//
//    // SMOKE: Verify Contacts module opens
//    @Test(groups = {"Smoke"})
//    public void verifyContactsModuleOpens() {
//
//        ContactPage contact = new ContactPage(driver);
//
//        contact.clickContactsTab();
//
//        Assert.assertTrue(
//            contact.isContactsPageDisplayed(),
//            "Contacts module did not open"
//        );
//
//        Reporter.log(
//            "PASS: Contacts module opened successfully",
//            true
//        );
//    }
//
//    // FUNCTIONAL: Create a contact with valid data
//    @Test(groups = {"Functional", "Positive"})
//    public void verifyCreateContact() throws Exception {
//
//        ContactPage contact = new ContactPage(driver);
//
//        String firstName = eu.getDataFromExcel("contact",1, 0);
//        String lastName = eu.getDataFromExcel("contact",1, 1);
//        String email = eu.getDataFromExcel("contact",1, 2);
//
//        contact.clickContactsTab();
//        contact.clickCreateContact();
//
//        contact.enterFirstName(firstName);
//        contact.enterLastName(lastName);
//        contact.enterEmail(email);
//
//        contact.clickSaveContact();
//
//        failIfAlertPresent(contact);
//
//        Assert.assertTrue(
//            contact.isContactDetailsDisplayed(lastName, email),
//            "Contact details were not displayed after saving"
//        );
//
//        Reporter.log(
//            "PASS: Contact created successfully: " + email,
//            true
//        );
//    }
//
//    // INTEGRATION: Associate a contact with an organization
//
//	@Test(groups = {"Integration"})
//	public void verifyContactOrganizationIntegration() throws IOException {
//	
//	    // Read organization name and contact data from Excel
//	    String organizationName =
//	            eu.getDataFromExcel("Contact", 3, 3);
//	
//	    String firstName =
//	            eu.getDataFromExcel("Contact", 3, 0);
//	
//	    String lastName =
//	            eu.getDataFromExcel("Contact", 3, 1);
//	
//	    String email =
//	            eu.getDataFromExcel("Contact", 3, 2);
//		
//	    // STEP 2: Open Contacts and create the contact
//	    ContactPage contactPage = new ContactPage(driver);
//	
//	    contactPage.clickContactsTab();
//	    contactPage.clickCreateContact();
//	    contactPage.enterFirstName(firstName);
//	    contactPage.enterLastName(lastName);
//	    contactPage.enterEmail(email);
//	
//	    // STEP 3: Associate the contact with the organization
//	    contactPage.selectOrganization(organizationName);
//	
//	    // STEP 4: Save the contact
//	    contactPage.clickSaveContact();
//	
//	    // STEP 5: Verify the contact was saved
//	    Assert.assertTrue(
//	            contactPage.isContactDetailsDisplayed(lastName, email),
//	            "Contact was not created successfully."
//	    );
//	
//	    // STEP 6: Verify organization association
//	    Assert.assertTrue(
//	            contactPage.isOrganizationAssociationDisplayed(organizationName),
//	            "Contact was not associated with the organization."
//	    );
//	}
//
//
//    // SYSTEM: Create a contact and verify persisted details
//    @Test(groups = {"System"})
//    public void verifyContactDetailsPersistAfterRefresh()
//            throws Exception {
//
//        ContactPage contact = new ContactPage(driver);
//
//        String firstName = eu.getDataFromExcel("contact",4, 0);
//        String lastName = eu.getDataFromExcel("contact",4, 1);
//        String email = eu.getDataFromExcel("contact",4, 2);
//
//        contact.clickContactsTab();
//        contact.clickCreateContact();
//
//        contact.enterFirstName(firstName);
//        contact.enterLastName(lastName);
//        contact.enterEmail(email);
//
//        contact.clickSaveContact();
//
//        failIfAlertPresent(contact);
//
//        Assert.assertTrue(
//            contact.isContactDetailsDisplayed(lastName, email),
//            "Contact details were not displayed after saving"
//        );
//
//        // Refresh to check that the details persist
//        driver.navigate().refresh();
//
//        Assert.assertTrue(
//            contact.isContactDetailsDisplayed(lastName, email),
//            "Contact details did not persist after refresh"
//        );
//
//        Reporter.log(
//            "PASS: Contact details persisted after refresh: " + email,
//            true
//        );
//    }
//}





package contactscript;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.ExcelUtility;
import objrepo.ContactPage;
import objrepo.OrganizationPage;

public class ContactTest extends BaseClass {

    ExcelUtility eu = new ExcelUtility();

    // Handle application validation alerts
    private void failIfAlertPresent(ContactPage contactPage) {
        if (contactPage.isAlertPresent()) {
            String message = contactPage.getAlertText();
            contactPage.acceptAlert();

            Assert.fail("Application validation alert: " + message);
        }
    }

    // 1. SMOKE TEST: Verify Contacts module opens
    @Test(groups = {"Smoke"})
    public void verifyContactsModuleOpens() {

        ContactPage contactPage = new ContactPage(driver);

        contactPage.clickContactsTab();

        Assert.assertTrue(
                contactPage.isContactsPageDisplayed(),
                "Contacts module did not open successfully."
        );

        Reporter.log(
                "PASS: Contacts module opened successfully.",
                true
        );
    }

    // 2. FUNCTIONAL TEST: Create a contact
    @Test(groups = {"Functional", "Positive"})
    public void verifyCreateContact() throws IOException {

        String firstName =
                eu.getDataFromExcel("Contact", 1, 0);

        String lastName =
                eu.getDataFromExcel("Contact", 1, 1);

        String email =
                eu.getDataFromExcel("Contact", 1, 2);

        ContactPage contactPage = new ContactPage(driver);

        contactPage.clickContactsTab();
        contactPage.clickCreateContact();

        contactPage.enterFirstName(firstName);
        contactPage.enterLastName(lastName);
        contactPage.enterEmail(email);

        failIfAlertPresent(contactPage);

        contactPage.clickSaveContact();

        failIfAlertPresent(contactPage);

        Assert.assertTrue(
                contactPage.isContactDetailsDisplayed(lastName, email),
                "Contact details were not displayed after saving."
        );

        Reporter.log(
                "PASS: Contact created successfully: " + email,
                true
        );
    }

    // 3. INTEGRATION TEST: Create an organization and associate a contact
    @Test(groups = {"Integration", "Positive"})
    public void verifyContactOrganizationIntegration()
            throws IOException {

        String baseOrganizationName =
                eu.getDataFromExcel("Contact", 3, 3);

        String firstName =
                eu.getDataFromExcel("Contact", 3, 0);

        String lastName =
                eu.getDataFromExcel("Contact", 3, 1);

        // Unique values prevent duplicate-data errors
        String uniqueId = String.valueOf(System.currentTimeMillis());

        String email = "integration" + uniqueId + "@example.com";

        String organizationName =
                baseOrganizationName + "_" + uniqueId;

        OrganizationPage organizationPage =
                new OrganizationPage(driver);

        // Step 1: Open Organizations and create an organization
        organizationPage.clickOrganizationsTab();
        organizationPage.clickCreateOrganization();
        organizationPage.enterOrganizationName(organizationName);
        organizationPage.clickSaveOrganization();

        // Step 2: Handle an alert only if one actually appears
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.alertIsPresent());

            String alertMessage = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();

            Assert.fail(
                    "Organization creation failed: " + alertMessage
            );

        } catch (TimeoutException e) {
            // No alert appeared; continue with verification.
        }

        // Step 3: Open Contacts
        ContactPage contactPage = new ContactPage(driver);

        contactPage.clickContactsTab();
        contactPage.clickCreateContact();

        // Step 4: Enter contact details
        contactPage.enterFirstName(firstName);
        contactPage.enterLastName(lastName);
        contactPage.enterEmail(email);

        // Step 5: Select the organization through the lookup popup
        contactPage.selectOrganization(organizationName);

        // Step 6: Save the contact
        contactPage.clickSaveContact();

        failIfAlertPresent(contactPage);

        // Step 7: Verify contact details
        Assert.assertTrue(
                contactPage.isContactDetailsDisplayed(lastName, email),
                "Contact details were not saved successfully."
        );

        // Step 8: Verify organization association
        Assert.assertTrue(
                contactPage.isOrganizationAssociationDisplayed(
                        organizationName
                ),
                "Contact was not associated with the organization."
        );

        Reporter.log(
                "PASS: Contact created and associated with organization: "
                        + organizationName,
                true
        );
    }

    // 4. SYSTEM TEST: Verify contact persists after refresh
    @Test(groups = {"System", "Positive"})
    public void verifyContactDetailsPersistAfterRefresh()
            throws IOException {

        String firstName =
                eu.getDataFromExcel("Contact", 4, 0);

        String lastName =
                eu.getDataFromExcel("Contact", 4, 1);

        String email =
                eu.getDataFromExcel("Contact", 4, 2);

        ContactPage contactPage = new ContactPage(driver);

        // Create the contact
        contactPage.clickContactsTab();
        contactPage.clickCreateContact();

        contactPage.enterFirstName(firstName);
        contactPage.enterLastName(lastName);
        contactPage.enterEmail(email);

        failIfAlertPresent(contactPage);

        contactPage.clickSaveContact();

        failIfAlertPresent(contactPage);

        Assert.assertTrue(
                contactPage.isContactDetailsDisplayed(lastName, email),
                "Contact was not displayed after saving."
        );

        // Refresh the page
        driver.navigate().refresh();

        Assert.assertTrue(
                contactPage.isContactDetailsDisplayed(lastName, email),
                "Contact details did not persist after refreshing."
        );

        Reporter.log(
                "PASS: Contact details persisted after refresh: " + email,
                true
        );
    }
}