//package objrepo;
//
//import java.time.Duration;
//import java.util.Set;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class ContactPage {
//
//    private final WebDriver driver;
//    private final WebDriverWait wait;
//
//    public ContactPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(
//                driver, Duration.ofSeconds(15));
//    }
//
//    // Open Contacts module
//    public void clickContactsTab() {
//        By contactsTab = By.xpath(
//                "//a[normalize-space()='CONTACTS' " +
//                "or normalize-space()='Contacts']");
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(contactsTab)
//        ).click();
//    }
//
//    // Verify Contacts module
//    public boolean isContactsPageDisplayed() {
//        try {
//            return wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.xpath(
//                                    "//*[normalize-space()='Contacts' " +
//                                    "or normalize-space()='CONTACTS']")
//                    )
//            ).isDisplayed();
//
//        } catch (TimeoutException e) {
//            return false;
//        }
//    }
//
//    // Open Create Contact form
//    public void clickCreateContact() {
//        By createButton = By.xpath(
//                "//img[contains(@title,'Create Contact')]" +
//                " | //a[contains(@href,'module=Contacts') " +
//                "and contains(@href,'action=EditView')]"
//        );
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(createButton)
//        ).click();
//
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.name("lastname"))
//        );
//    }
//
//    // Enter First Name
//    public void enterFirstName(String firstName) {
//        WebElement field = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.name("firstname"))
//        );
//
//        field.clear();
//        field.sendKeys(firstName);
//    }
//
//    // Enter Last Name
//    public void enterLastName(String lastName) {
//        WebElement field = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.name("lastname"))
//        );
//
//        field.clear();
//        field.sendKeys(lastName);
//    }
//
//    // Enter Email
//    public void enterEmail(String email) {
//        WebElement field = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.name("email"))
//        );
//
//        field.clear();
//        field.sendKeys(email);
//    }
//
//    // Select an existing Organization using the lookup popup
//    public void selectOrganization(String organizationName) {
//
//        String parentWindow = driver.getWindowHandle();
//
//        By selectIcon = By.xpath(
//                "//input[@name='account_name']" +
//                "/following-sibling::img[@title='Select']"
//        );
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(selectIcon)
//        ).click();
//
//        String popupWindow = null;
//
//        try {
//            // Wait until the lookup popup opens
//            wait.until(d -> d.getWindowHandles().size() > 1);
//
//            // Identify the popup window
//            Set<String> windows = driver.getWindowHandles();
//
//            final String selectedPopup = windows.stream()
//                    .filter(window -> !window.equals(parentWindow))
//                    .findFirst()
//                    .orElseThrow(() -> new IllegalStateException(
//                            "Organization lookup popup did not open."));
//
//            popupWindow = selectedPopup;
//
//            driver.switchTo().window(selectedPopup);
//
//            // Enter organization name in popup search field
//            WebElement searchBox = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.name("search_text"))
//            );
//
//            searchBox.clear();
//            searchBox.sendKeys(organizationName);
//
//            // Click the confirmed Search Now button
//            By searchButton = By.cssSelector(
//                    "input[name='search']" +
//                    "[onclick*=\"callSearch('Basic')\"]"
//            );
//
//            wait.until(
//                    ExpectedConditions.elementToBeClickable(searchButton)
//            ).click();
//
//            // Wait for the search results page to load
//            wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.tagName("body")));
//
//            // Look for the exact organization link first
//            By exactOrganizationLink = By.xpath(
//                    "//a[normalize-space(.)='" +
//                    organizationName + "']"
//            );
//
//            // If the exact link is not found, inspect actual results
//            try {
//                wait.until(
//                        ExpectedConditions.elementToBeClickable(
//                                exactOrganizationLink)
//                ).click();
//
//            } catch (TimeoutException e) {
//
//                String resultText = driver.findElement(
//                        By.tagName("body")).getText();
//
//                System.out.println(
//                        "========== ORGANIZATION SEARCH RESULTS ==========");
//                System.out.println(resultText);
//                System.out.println(
//                        "================================================");
//
//                // Try a partial matching link
//                By partialOrganizationLink = By.xpath(
//                        "//a[contains(normalize-space(.), '" +
//                        organizationName + "')]"
//                );
//
//                try {
//                    wait.until(
//                            ExpectedConditions.elementToBeClickable(
//                                    partialOrganizationLink)
//                    ).click();
//
//                } catch (TimeoutException ex) {
//                    throw new IllegalStateException(
//                            "Could not find organization: " +
//                            organizationName +
//                            ". Check the search results printed in the console. " +
//                            "Confirm that the organization exists in Vtiger.",
//                            ex
//                    );
//                }
//            }
//
//            // Wait for popup to close, if selecting the record closes it
//            final String popupHandle = selectedPopup;
//
//            wait.until(d ->
//                    !d.getWindowHandles().contains(popupHandle));
//
//            // Return to the contact form
//            driver.switchTo().window(parentWindow);
//
//            // Verify the selected organization
//            wait.until(
//                    ExpectedConditions.textToBePresentInElementValue(
//                            By.name("account_name"),
//                            organizationName)
//            );
//
//        } finally {
//            // Restore the contact form window if it still exists
//            if (driver.getWindowHandles().contains(parentWindow)) {
//                driver.switchTo().window(parentWindow);
//            }
//        }
//    }
//
//    // Save contact
//    public void clickSaveContact() {
//        By saveButton = By.xpath(
//                "//input[@type='submit' and " +
//                "contains(translate(@value,'SAVE','save'),'save')]" +
//                " | //input[@name='button' " +
//                "and contains(@value,'Save')]" +
//                " | //button[normalize-space()='Save']"
//        );
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(saveButton)
//        ).click();
//    }
//
//    // Check whether an application alert is present
//    public boolean isAlertPresent() {
//        try {
//            new WebDriverWait(
//                    driver, Duration.ofSeconds(2))
//                    .until(ExpectedConditions.alertIsPresent());
//
//            return true;
//
//        } catch (TimeoutException e) {
//            return false;
//        }
//    }
//
//    // Get alert message
//    public String getAlertText() {
//        return driver.switchTo().alert().getText();
//    }
//
//    // Accept alert
//    public void acceptAlert() {
//        driver.switchTo().alert().accept();
//    }
//
//    // Verify saved contact details
//    public boolean isContactDetailsDisplayed(
//            String lastName, String email) {
//
//        try {
//            return wait.until(d -> {
//                String text = d.findElement(
//                        By.tagName("body")).getText();
//
//                return text.contains(lastName)
//                        && text.contains(email);
//            });
//
//        } catch (TimeoutException e) {
//            return false;
//        }
//    }
//
//    // Verify organization association
//    public boolean isOrganizationAssociationDisplayed(
//            String organizationName) {
//
//        try {
//            return wait.until(d ->
//                    d.findElement(By.tagName("body"))
//                            .getText()
//                            .contains(organizationName)
//            );
//
//        } catch (TimeoutException e) {
//            return false;
//        }
//    }
//}


//package objrepo;
//import java.time.Duration;
//import java.util.Set;
//import org.openqa.selenium.By;
//import org.openqa.selenium.StaleElementReferenceException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//public class ContactPage {
//    private final WebDriver driver;
//    private final WebDriverWait wait;
//    public ContactPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(
//                driver, Duration.ofSeconds(15));
//    }
//    // Open Contacts module
//    public void clickContactsTab() {
//        By contactsTab = By.xpath(
//            "//a[normalize-space()='CONTACTS' " +
//            "or normalize-space()='Contacts']"
//        );
//        for (int attempt = 1; attempt <= 3; attempt++) {
//            try {
//                WebElement element = wait.until(
//                    ExpectedConditions.elementToBeClickable(contactsTab)
//                );
//                element.click();
//                return;
//            } catch (StaleElementReferenceException e) {
//                if (attempt == 3) {
//                    throw e;
//                }
//            }
//        }
//    }
//    // Verify Contacts module
//    public boolean isContactsPageDisplayed() {
//        try {
//            return wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.xpath(
//                                    "//*[normalize-space()='Contacts' " +
//                                    "or normalize-space()='CONTACTS']")
//                    )
//            ).isDisplayed();
//        } catch (TimeoutException e) {
//            return false;
//        }
//    }
//    // Open Create Contact form
//    public void clickCreateContact() {
//        By createButton = By.xpath(
//                "//img[contains(@title,'Create Contact')]" +
//                " | //a[contains(@href,'module=Contacts') " +
//                "and contains(@href,'action=EditView')]"
//        );
//        wait.until(
//                ExpectedConditions.elementToBeClickable(createButton)
//        ).click();
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.name("lastname"))
//        );
//    }
//    // Enter First Name
//    public void enterFirstName(String firstName) {
//        WebElement field = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.name("firstname"))
//        );
//        field.clear();
//        field.sendKeys(firstName);
//    }
//    // Enter Last Name
//    public void enterLastName(String lastName) {
//        WebElement field = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.name("lastname"))
//        );
//        field.clear();
//        field.sendKeys(lastName);
//    }
//    // Enter Email
//    public void enterEmail(String email) {
//        WebElement field = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.name("email"))
//        );
//        field.clear();
//        field.sendKeys(email);
//    }
//    // Select an existing Organization using the lookup popup
//    public void selectOrganization(String organizationName) {
//        String parentWindow = driver.getWindowHandle();
//        By selectIcon = By.xpath(
//                "//input[@name='account_name']" +
//                "/following-sibling::img[@title='Select']"
//        );
//        wait.until(
//                ExpectedConditions.elementToBeClickable(selectIcon)
//        ).click();
//        String popupWindow = null;
//        try {
//            // Wait until the lookup popup opens
//            wait.until(d -> d.getWindowHandles().size() > 1);
//            // Identify the popup window
//            Set<String> windows = driver.getWindowHandles();
//            final String selectedPopup = windows.stream()
//                    .filter(window -> !window.equals(parentWindow))
//                    .findFirst()
//                    .orElseThrow(() -> new IllegalStateException(
//                            "Organization lookup popup did not open."));
//            popupWindow = selectedPopup;
//            driver.switchTo().window(selectedPopup);
//            // Enter organization name in popup search field
//            WebElement searchBox = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            By.name("search_text"))
//            );
//            searchBox.clear();
//            searchBox.sendKeys(organizationName);
//            // Click the confirmed Search Now button
//            By searchButton = By.cssSelector(
//                    "input[name='search']" +
//                    "[onclick*=\"callSearch('Basic')\"]"
//            );
//            wait.until(
//                    ExpectedConditions.elementToBeClickable(searchButton)
//            ).click();
//            // Wait for the search results page to load
//            wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.tagName("body")));
//            // Look for the exact organization link first
//            By exactOrganizationLink = By.xpath(
//                    "//a[normalize-space(.)='" +
//                    organizationName + "']"
//            );
//            // If the exact link is not found, inspect actual results
//            try {
//                wait.until(
//                        ExpectedConditions.elementToBeClickable(
//                                exactOrganizationLink)
//                ).click();
//            } catch (TimeoutException e) {
//                String resultText = driver.findElement(
//                        By.tagName("body")).getText();
//                System.out.println(
//                        "========== ORGANIZATION SEARCH RESULTS ==========");
//                System.out.println(resultText);
//                System.out.println(
//                        "================================================");
//                // Try a partial matching link
//                By partialOrganizationLink = By.xpath(
//                        "//a[contains(normalize-space(.), '" +
//                        organizationName + "')]"
//                );
//                try {
//                    wait.until(
//                            ExpectedConditions.elementToBeClickable(
//                                    partialOrganizationLink)
//                    ).click();
//                } catch (TimeoutException ex) {
//                    throw new IllegalStateException(
//                            "Could not find organization: " +
//                            organizationName +
//                            ". Check the search results printed in the console. " +
//                            "Confirm that the organization exists in Vtiger.",
//                            ex
//                    );
//                }
//            }
//            // Wait for popup to close, if selecting the record closes it
//            final String popupHandle = selectedPopup;
//            wait.until(d ->
//                    !d.getWindowHandles().contains(popupHandle));
//            // Return to the contact form
//            driver.switchTo().window(parentWindow);
//            // Verify the selected organization
//            wait.until(
//                    ExpectedConditions.textToBePresentInElementValue(
//                            By.name("account_name"),
//                            organizationName)
//            );
//        } finally {
//            // Restore the contact form window if it still exists
//            if (driver.getWindowHandles().contains(parentWindow)) {
//                driver.switchTo().window(parentWindow);
//            }
//        }
//    }
//    // Save contact
//    public void clickSaveContact() {
//        By saveButton = By.xpath(
//                "//input[@type='submit' and " +
//                "contains(translate(@value,'SAVE','save'),'save')]" +
//                " | //input[@name='button' " +
//                "and contains(@value,'Save')]" +
//                " | //button[normalize-space()='Save']"
//        );
//        wait.until(
//                ExpectedConditions.elementToBeClickable(saveButton)
//        ).click();
//    }
//    // Check whether an application alert is present
//    public boolean isAlertPresent() {
//        try {
//            new WebDriverWait(
//                    driver, Duration.ofSeconds(2))
//                    .until(ExpectedConditions.alertIsPresent());
//            return true;
//        } catch (TimeoutException e) {
//            return false;
//        }
//    }
//    // Get alert message
//    public String getAlertText() {
//        return driver.switchTo().alert().getText();
//    }
//    // Accept alert
//    public void acceptAlert() {
//        driver.switchTo().alert().accept();
//    }
//    // Verify saved contact details
//    public boolean isContactDetailsDisplayed(
//            String lastName, String email) {
//        try {
//            return wait.until(d -> {
//                String text = d.findElement(
//                        By.tagName("body")).getText();
//                return text.contains(lastName)
//                        && text.contains(email);
//            });
//        } catch (TimeoutException e) {
//            return false;
//        }
//    }
//    // Verify organization association
//    public boolean isOrganizationAssociationDisplayed(
//            String organizationName) {
//        try {
//            return wait.until(d ->
//                    d.findElement(By.tagName("body"))
//                            .getText()
//                            .contains(organizationName)
//            );
//        } catch (TimeoutException e) {
//            return false;
//        }
//    }
//}


package objrepo;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));
    }

    // Handle an unexpected JavaScript alert
    private boolean acceptAlertIfPresent() {
        try {
            driver.switchTo().alert().accept();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    // Open Contacts module
    public void clickContactsTab() {

        By contactsTab = By.xpath(
                "//a[normalize-space()='CONTACTS' " +
                "or normalize-space()='Contacts']");

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement tab = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                contactsTab));

                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].scrollIntoView(" +
                                "{block:'center'});", tab);

                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                contactsTab)).click();

                return;

            } catch (StaleElementReferenceException |
                     ElementClickInterceptedException e) {

                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }

    // Verify Contacts module
    public boolean isContactsPageDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//*[normalize-space()='Contacts' " +
                                    "or normalize-space()='CONTACTS']")))
                    .isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    // Open Create Contact form
    public void clickCreateContact() {

        By createButton = By.xpath(
                "//img[contains(@title,'Create Contact')]" +
                " | //a[contains(@href,'module=Contacts') " +
                "and contains(@href,'action=EditView')]");

        wait.until(
                ExpectedConditions.elementToBeClickable(createButton))
                .click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("lastname")));
    }

    // Enter First Name
    public void enterFirstName(String firstName) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("firstname")));

        field.clear();
        field.sendKeys(firstName);
    }

    // Enter Last Name
    public void enterLastName(String lastName) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("lastname")));

        field.clear();
        field.sendKeys(lastName);
    }

    // Enter Email
    public void enterEmail(String email) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("email")));

        field.clear();
        field.sendKeys(email);
    }

    // Select an existing organization using WindowHandlePage
    public void selectOrganization(String organizationName) {

        String parentWindow = driver.getWindowHandle();

        By selectIcon = By.xpath(
                "//input[@name='account_name']" +
                "/following-sibling::img[@title='Select']");

        wait.until(
                ExpectedConditions.elementToBeClickable(selectIcon))
                .click();

        try {
            // Wait until lookup popup opens
            wait.until(d -> d.getWindowHandles().size() > 1);

            Set<String> handles = driver.getWindowHandles();

            String popupWindow = handles.stream()
                    .filter(handle -> !handle.equals(parentWindow))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Organization lookup popup did not open."));

            WindowHandlePage windowPage =
                    new WindowHandlePage(driver);

            // Search and select organization in popup
            driver.switchTo().window(popupWindow);

            windowPage.searchOrganization(organizationName);
            windowPage.selectOrganization(organizationName);

            // Wait until popup closes
            wait.until(d ->
                    !d.getWindowHandles().contains(popupWindow));

        } finally {
            // Restore original contact form window
            if (driver.getWindowHandles().contains(parentWindow)) {
                driver.switchTo().window(parentWindow);
            }
        }

        // Verify organization was populated in the contact form
        wait.until(
                ExpectedConditions.textToBePresentInElementValue(
                        By.name("account_name"),
                        organizationName));
    }

    // Save Contact
    public void clickSaveContact() {

        By saveButton = By.xpath(
                "//input[@type='submit' and " +
                "contains(translate(@value,'SAVE','save'),'save')]" +
                " | //input[@name='button' " +
                "and contains(@value,'Save')]" +
                " | //button[normalize-space()='Save']");

        wait.until(
                ExpectedConditions.elementToBeClickable(saveButton))
                .click();
    }

    // Check whether an application alert is present
    public boolean isAlertPresent() {
        try {
            new WebDriverWait(
                    driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.alertIsPresent());

            return true;

        } catch (TimeoutException e) {
            return false;
        }
    }

    // Get alert message
    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    // Accept alert
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    // Verify saved contact details
    public boolean isContactDetailsDisplayed(
            String lastName, String email) {

        try {
            return wait.until(d -> {
                String bodyText = d.findElement(
                        By.tagName("body")).getText();

                return bodyText.contains(lastName)
                        && bodyText.contains(email);
            });

        } catch (TimeoutException e) {
            return false;
        }
    }

    // Verify organization association
    public boolean isOrganizationAssociationDisplayed(
            String organizationName) {

        try {
            return wait.until(d ->
                    d.findElement(By.tagName("body"))
                            .getText()
                            .contains(organizationName));

        } catch (TimeoutException e) {
            return false;
        }
    }
}