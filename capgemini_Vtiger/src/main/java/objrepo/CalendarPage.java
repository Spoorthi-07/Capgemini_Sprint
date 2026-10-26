package objrepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalendarPage {

    WebDriver driver;
    WebDriverWait wait;

    // =============== CONSTRUCTOR ===============

    public CalendarPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // =============== CALENDAR TAB ===============

    @FindBy(xpath = "//a[normalize-space()='Calendar']")
    private WebElement calendarTab;

    public void clickCalendarTab() {
        wait.until(
                ExpectedConditions.elementToBeClickable(calendarTab))
                .click();
    }

    // =============== CREATE CALL FORM ===============

    @FindBy(xpath = "//td[normalize-space()='Add']")
    private WebElement addMenu;

    @FindBy(xpath = "//td[normalize-space()='Call']")
    private WebElement callOption;

    public void openCreateCallForm() {
        wait.until(
                ExpectedConditions.elementToBeClickable(addMenu))
                .click();

        wait.until(
                ExpectedConditions.elementToBeClickable(callOption))
                .click();
    }

    // =============== EVENT NAME ===============

    @FindBy(xpath =
        "//input[@name='subject' or @id='eventname']")
    private WebElement eventName;

    public void enterEventName(String name) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(eventName));
        element.clear();
        element.sendKeys(name);
    }

    // =============== DESCRIPTION ===============

    @FindBy(xpath =
        "//textarea[@name='description' or @id='description']")
    private WebElement description;

    public void enterDescription(String text) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(description));
        element.clear();
        element.sendKeys(text);
    }

    // =============== LOCATION ===============

    @FindBy(xpath =
        "//input[@name='location' or @id='location']")
    private WebElement location;

    public void enterLocation(String place) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(location));
        element.clear();
        element.sendKeys(place);
    }

    // =============== EVENT TYPE ===============

    @FindBy(xpath =
        "//div[contains(@id,'addEvent')]//select[1]")
    private WebElement eventTypeDropdown;

    public void selectEventTypeByIndex(int index) {
        new Select(wait.until(
                ExpectedConditions.visibilityOf(eventTypeDropdown)))
                .selectByIndex(index);
    }

    // =============== STATUS ===============

    @FindBy(xpath =
        "//select[option[normalize-space()='Planned'] and " +
        "option[normalize-space()='Held']]")
    private WebElement statusDropdown;

    public void selectStatusByIndex(int index) {
        new Select(wait.until(
                ExpectedConditions.visibilityOf(statusDropdown)))
                .selectByIndex(index);
    }

    // =============== ASSIGNED TO ===============
    // Confirm this locator against the actual form HTML.

    @FindBy(name = "assigned_user_id")
    private WebElement assignedToDropdown;

    public void selectAssignedToByIndex(int index) {
        new Select(wait.until(
                ExpectedConditions.visibilityOf(assignedToDropdown)))
                .selectByIndex(index);
    }

    // =============== PRIORITY ===============

    @FindBy(xpath =
        "//select[option[normalize-space()='High'] and " +
        "option[normalize-space()='Medium']]")
    private WebElement priorityDropdown;

    public void selectPriorityByIndex(int index) {
        new Select(wait.until(
                ExpectedConditions.visibilityOf(priorityDropdown)))
                .selectByIndex(index);
    }

    // =============== START TIME ===============

    @FindBy(xpath =
        "//*[contains(normalize-space(),'Event starts at')]" +
        "/following::select[1]")
    private WebElement startHourDropdown;

    @FindBy(xpath =
        "//*[contains(normalize-space(),'Event starts at')]" +
        "/following::select[2]")
    private WebElement startMinuteDropdown;

    public void selectStartHourByIndex(int index) {
        new Select(wait.until(
                ExpectedConditions.visibilityOf(startHourDropdown)))
                .selectByIndex(index);
    }

    public void selectStartMinuteByIndex(int index) {
        new Select(wait.until(
                ExpectedConditions.visibilityOf(startMinuteDropdown)))
                .selectByIndex(index);
    }

    // =============== END TIME ===============

    @FindBy(xpath =
        "//*[contains(normalize-space(),'Event ends on') or " +
        "contains(normalize-space(),'Event ends at')]" +
        "/following::select[1]")
    private WebElement endHourDropdown;

    @FindBy(xpath =
        "//*[contains(normalize-space(),'Event ends on') or " +
        "contains(normalize-space(),'Event ends at')]" +
        "/following::select[2]")
    private WebElement endMinuteDropdown;

    public void selectEndHourByIndex(int index) {
        new Select(wait.until(
                ExpectedConditions.visibilityOf(endHourDropdown)))
                .selectByIndex(index);
    }

    public void selectEndMinuteByIndex(int index) {
        new Select(wait.until(
                ExpectedConditions.visibilityOf(endMinuteDropdown)))
                .selectByIndex(index);
    }

    // =============== AVAILABLE USERS ===============

    // ID confirmed in your screenshot.
    @FindBy(id = "availableusers")
    private WebElement availableUsers;

    // =============== ADD USER BUTTON ===============

    @FindBy(xpath =
        "//input[@value='Add >>' or @value='Add >']")
    private WebElement addUserButton;

    // =============== SELECTED USERS ===============
    // Verify this ID against the actual form HTML.

    @FindBy(id = "selectedusers")
    private WebElement selectedUsers;

    // Click Available Users, move down, and add the user.
    public String selectUserUsingDownArrowAndAdd() {

        WebElement list = wait.until(
                ExpectedConditions.elementToBeClickable(availableUsers));

        list.click();

        new Actions(driver)
                .sendKeys(Keys.ARROW_DOWN)
                .perform();

        Select availableSelect = new Select(list);

        String userName = availableSelect
                .getFirstSelectedOption()
                .getText()
                .trim();

        wait.until(
                ExpectedConditions.elementToBeClickable(addUserButton))
                .click();

        return userName;
    }

    // Verify the user in Selected Users.
    public boolean isUserSelected(String userName) {

        Select selectedSelect = new Select(
                wait.until(
                    ExpectedConditions.visibilityOf(selectedUsers)));

        List<WebElement> options = selectedSelect.getOptions();

        for (WebElement option : options) {
            if (option.getText().trim().equals(userName)) {
                return true;
            }
        }

        return false;
    }

    // =============== SAVE ===============

    public void clickSave() {

        By saveLocator = By.xpath(
            "//input[@name='eventsave' or @value='Save' " +
            "or @title='Save']" +
            " | //button[normalize-space()='Save']"
        );

        // Wait until at least one Save button is visible and enabled.
        wait.until(driver -> {
            List<WebElement> buttons =
                    driver.findElements(saveLocator);

            for (WebElement button : buttons) {
                if (button.isDisplayed() && button.isEnabled()) {
                    return true;
                }
            }
            return false;
        });

        List<WebElement> buttons =
                driver.findElements(saveLocator);

        // Click the last visible, enabled Save button.
        for (int i = buttons.size() - 1; i >= 0; i--) {

            WebElement button = buttons.get(i);

            if (button.isDisplayed() && button.isEnabled()) {

                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript(
                            "arguments[0].scrollIntoView(" +
                            "{block:'center'});",
                            button
                        );

                wait.until(
                    ExpectedConditions.elementToBeClickable(button))
                    .click();

                return;
            }
        }

        throw new NoSuchElementException(
                "No visible and enabled Save button was found.");
    }

    // =============== ALERT HANDLING ===============

    public String handleDateTimeAlert() {

        Alert alert = wait.until(
                ExpectedConditions.alertIsPresent());

        String message = alert.getText();
        alert.accept();

        return message;
    }
    
 // Click a Meeting event by its name
    public void clickMeetingEvent(String meetingName) {

        By meetingLocator = By.xpath(
            "//div[@id='ListViewContents']//a[normalize-space()='" 
            + meetingName + "']"
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(meetingLocator)
        ).click();
    }

    // Select the event checkbox by event name
    public void selectMeetingCheckbox(String meetingName) {

        By checkboxLocator = By.xpath(
            "//tr[.//a[normalize-space()='" + meetingName + "']]"
            + "//input[@type='checkbox' and @name='selected_id']"
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(checkboxLocator)
        ).click();
    }

    // Click Change Owner
    @FindBy(xpath =
        "//input[@value='Change Owner' or @title='Change Owner']")
    private WebElement changeOwnerButton;

    public void clickChangeOwner() {
        wait.until(
            ExpectedConditions.elementToBeClickable(changeOwnerButton)
        ).click();
    }
    
   

    @FindBy(id = "user_checkbox")
    private WebElement userRadioButton;

    @FindBy(id = "group_checkbox")
    private WebElement groupRadioButton;

    @FindBy(id = "lead_owner")
    private WebElement ownerDropdown;

    @FindBy(id = "lead_group_owner")
    private WebElement groupDropdown;

    public void clickOwnership(int index) {

        // Select User ownership
        wait.until(
            ExpectedConditions.elementToBeClickable(userRadioButton)
        ).click();

        // Select the owner from the visible dropdown
        WebElement dropdown = wait.until(
            ExpectedConditions.visibilityOf(ownerDropdown)
        );

        new Select(dropdown).selectByIndex(index);
    }
  
    
    @FindBy(xpath = "//input[@value='Update Owner']")
    private WebElement updateOwnerButton;

    public void clickUpdateOwner() {
        wait.until(
            ExpectedConditions.elementToBeClickable(updateOwnerButton)
        ).click();
    }
    
    public void clickAllEventsAndTodos() {

        By allEventsLocator = By.xpath(
            "//a[normalize-space()='All Events & Todos']"
        );

        WebElement link = wait.until(
            ExpectedConditions.visibilityOfElementLocated(allEventsLocator)
        );

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                    "arguments[0].click();",
                    link
                );

        wait.until(
            ExpectedConditions.urlContains("action=ListView")
        );
    }
    
    public boolean isEventDisplayed(String eventName) {

        By eventLocator = By.xpath(
            "//*[@id='ListViewContents']" +
            "//*[self::a or self::td]" +
            "[normalize-space()='" + eventName + "']"
        );

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(eventLocator)
            ).isDisplayed();

        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}