package objrepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WindowHandlePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WindowHandlePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));
    }

    // Search for an organization in the lookup popup
    public void searchOrganization(String organizationName) {

        // Wait for the popup search field
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("search_text")));

        searchBox.clear();
        searchBox.sendKeys(organizationName);

        // Click Search Now
        By searchButton = By.cssSelector(
                "input[name='search']" +
                "[onclick*=\"callSearch('Basic')\"]");

        wait.until(
                ExpectedConditions.elementToBeClickable(searchButton))
                .click();

        // Wait for the search results page to finish updating
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.tagName("body")));
    }

    // Select the matching organization from the results
    public void selectOrganization(String organizationName) {

        // Use an XPath literal that safely handles apostrophes
        String xpathLiteral = toXPathLiteral(organizationName);

        By organizationLink = By.xpath(
                "//a[normalize-space(.)=" + xpathLiteral + "]");

        try {
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            organizationLink))
                    .click();

        } catch (TimeoutException e) {

            String resultText = driver.findElement(
                    By.tagName("body")).getText();

            throw new IllegalStateException(
                    "Organization not found in lookup results: "
                            + organizationName
                            + "\nPopup page content:\n"
                            + resultText,
                    e);
        }
    }

    // Convert Java string to a valid XPath string literal
    private String toXPathLiteral(String value) {

        if (!value.contains("'")) {
            return "'" + value + "'";
        }

        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }

        String[] parts = value.split("'", -1);
        StringBuilder xpath = new StringBuilder("concat(");

        for (int i = 0; i < parts.length; i++) {

            if (i > 0) {
                xpath.append(", \"'\", ");
            }

            xpath.append("'").append(parts[i]).append("'");
        }

        xpath.append(")");
        return xpath.toString();
    }
}