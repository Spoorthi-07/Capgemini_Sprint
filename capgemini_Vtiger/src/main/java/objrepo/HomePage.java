//package objrepo;
//
//import java.time.Duration;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class HomePage {
//
//    WebDriver driver;
//    WebDriverWait wait;
//
//    public HomePage(WebDriver driver) {
//        this.driver = driver;
//        PageFactory.initElements(driver, this);
//
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        
//    }
//	@FindBy(xpath = "//img[contains(@onclick,'fnAddWindow')]")
//	private WebElement addWidgetButton;
//
//	public void clickAddWidget() {
//	    addWidgetButton.click();
//	}
//	@FindBy(id = "addmodule")
//	private WebElement moduleOption;
//	
//	public void clickModuleOption() {
//	    moduleOption.click();
//	}
//	
//
//	@FindBy(id = "stufftitle_id")
//	private WebElement windowTitle;
//	
//	public void addWindowTitle(String title) {
//	    WebElement textbox = wait.until(
//	            ExpectedConditions.visibilityOf(windowTitle)
//	    );
//	
//	    wait.until(
//	            ExpectedConditions.elementToBeClickable(windowTitle)
//	    );
//	
//	    textbox.clear();
//	    textbox.sendKeys(title);
//	}
//
//	
//
//	@FindBy(id="adddash")
//	private WebElement dashboardOption;
//	
//	public void clickDashboardOption() {
//	    dashboardOption.click();
//	}
//	
//	@FindBy(id="savebtn")
//	private WebElement saveBtn;
//	
//	public void clickSaveBtn() {
//		saveBtn.click();
//	}
//	
//  
//	@FindBy(id = "seldashbd_id")
//	private WebElement dashboardNameDropdown;
//
//	public void selectdashnameByIndex(int index) {
//	    new Select(dashboardNameDropdown).selectByIndex(index);
//	}
//    
// 
//    @FindBy(id = "seldashtype_id")
//    private WebElement dashtypeByDropdown;
//    
//    public void selectdashtypeByIndex(int index) {
//        new Select(dashtypeByDropdown).selectByIndex(index);
//    }
//    
//    
// // Notebook option in Add Widget menu
//    @FindBy(id = "addNotebook")
//    private WebElement notebookOption;
//
//    public void clickNotebookOption() {
//        wait.until(ExpectedConditions.elementToBeClickable(notebookOption))
//            .click();
//    }
//
// // Window Title textbox
//    @FindBy(id = "stufftitle_id")
//    private WebElement windowTitleTextbox;
//
//    public void enterWindowTitle(String title) {
//        WebElement textbox = wait.until(
//            ExpectedConditions.visibilityOf(windowTitleTextbox)
//        );
//
//        textbox.clear();
//
//        if (title != null && !title.trim().isEmpty()) {
//            textbox.sendKeys(title);
//        }
//    }
//
//   @FindBy(id = "savebtn")
//    private WebElement saveButton;
//
//    public void clickSave() {
//        wait.until(ExpectedConditions.elementToBeClickable(saveButton))
//            .click();
//    }
//    
//    public boolean isWindowTitleDisplayed() {
//        try {
//            return wait.until(
//                ExpectedConditions.visibilityOf(windowTitleTextbox)
//            ).isDisplayed();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//	
//}


package objrepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);

        wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));
    }

    // Add Widget button
    @FindBy(xpath = "//img[contains(@onclick,'fnAddWindow')]")
    private WebElement addWidgetButton;

    public void clickAddWidget() {
        wait.until(
                ExpectedConditions.elementToBeClickable(addWidgetButton)
        ).click();
    }

    // Module option
    @FindBy(id = "addmodule")
    private WebElement moduleOption;

    public void clickModuleOption() {
        wait.until(
                ExpectedConditions.elementToBeClickable(moduleOption)
        ).click();
    }

    // Dashboard option
    @FindBy(id = "adddash")
    private WebElement dashboardOption;

    public void clickDashboardOption() {
        wait.until(
                ExpectedConditions.elementToBeClickable(dashboardOption)
        ).click();
    }

    // Notebook option
    @FindBy(id = "addNotebook")
    private WebElement notebookOption;

    public void clickNotebookOption() {
        wait.until(
                ExpectedConditions.elementToBeClickable(notebookOption)
        ).click();
    }

    // Window title textbox
    @FindBy(id = "stufftitle_id")
    private WebElement windowTitle;

    public void addWindowTitle(String title) {
        WebElement textbox = wait.until(
                ExpectedConditions.visibilityOf(windowTitle)
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(windowTitle)
        );

        textbox.clear();

        if (title != null && !title.trim().isEmpty()) {
            textbox.sendKeys(title);
        }
    }

    // Dashboard name dropdown
    @FindBy(id = "seldashbd_id")
    private WebElement dashboardNameDropdown;

    public void selectdashnameByIndex(int index) {
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        dashboardNameDropdown)
        );

        new Select(dropdown).selectByIndex(index);
    }

    // Dashboard type dropdown
    @FindBy(id = "seldashtype_id")
    private WebElement dashboardTypeDropdown;

    public void selectdashtypeByIndex(int index) {
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        dashboardTypeDropdown)
        );

        new Select(dropdown).selectByIndex(index);
    }

    // Save button
    @FindBy(id = "savebtn")
    private WebElement saveButton;

    public void clickSaveBtn() {
        wait.until(
                ExpectedConditions.elementToBeClickable(saveButton)
        ).click();
    }

    // Verify window title textbox is displayed
    public boolean isWindowTitleDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOf(windowTitle)
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    // Verify window title field contains the expected value
    public boolean isWindowTitleCorrect(String expectedTitle) {
        try {
            return wait.until(d -> {
                WebElement field = d.findElement(
                        By.id("stufftitle_id"));

                return field.isDisplayed()
                        && expectedTitle.equals(field.getAttribute("value"));
            });

        } catch (Exception e) {
            return false;
        }
    }
}