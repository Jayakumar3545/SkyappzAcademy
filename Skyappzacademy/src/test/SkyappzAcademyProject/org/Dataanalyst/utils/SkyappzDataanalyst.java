package org.Dataanalyst.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class SkyappzDataanalyst implements Seleniuminterface {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private ExtentReports extent;
    private ExtentTest test;

    // Setup WebDriver with common settings for all browsers
    @BeforeClass
    public void setupWebDriver() {
        extent = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("C:\\Users\\ADMIN\\Downloads\\Newfolders\\Dataanalyst.html");
        extent.attachReporter(sparkReporter);
    }

    @Test(priority = 1)
    public void runFirefoxTests() {
        closePreviousDriver();
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\ADMIN\\Downloads\\geckodriver-v0.32.2-win64\\geckodriver.exe");

        driver = new FirefoxDriver();
        driver.get("https://skyappzacademy.com/data-analyst");
        driver.manage().window().maximize();
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));// WebDriverWait initialization
        js = (JavascriptExecutor) driver; // Initialize JavascriptExecutor
        runBrowserTests("Firefox");
    }

    @Test(priority = 2)
    public void runChromeTests() {
        closePreviousDriver();
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\Downloads\\chromedriver-win64\\chromedriver.exe");
        
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // WebDriverWait initialization
        js = (JavascriptExecutor) driver; // Initialize JavascriptExecutor
        driver.get("https://skyappzacademy.com/data-analyst");
        runBrowserTests("Chrome");
    }

    @Test(priority = 3)
    public void runEdgeTests() {
        closePreviousDriver();
        System.setProperty("webdriver.edge.driver", "C:\\Users\\ADMIN\\Downloads\\edgedriver_win64\\msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("start-maximized"); // Set Edge options
        
        driver = new EdgeDriver(options);
        driver.get("https://skyappzacademy.com/data-analyst");
        driver.manage().window().maximize();
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // WebDriverWait initialization
        js = (JavascriptExecutor) driver; // Initialize JavascriptExecutor
        runBrowserTests("Edge");
    }

    // Common test logic for all browsers
    public void runBrowserTests(String browserName) {
        test = extent.createTest("Test for " + browserName);
        System.out.println("Running tests for: " + browserName);

        // Example: Validate sections
        String[] sections = {"Home", "About Us", "Programming Overview", "Syllabus", "Certification", "Placement", "Reach Us"};
        for (String section : sections) {
            clickAndCapture("//a[text()='" + section + "']", section.trim());
        }

        // Additional specific actions for each section
        handleProgrammingOverview();
        handleSyllabusSection();
        handleCertificationDownload();
        handlePlacement();
        handleReachUsSection();
    }

    // Helper method to click and capture screenshot
    private void clickAndCapture(String xpath, String section) {
        try {
            WebElement menuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            js.executeScript("arguments[0].scrollIntoView(true);", menuItem);
            menuItem.click();
            test.pass("Clicked on: " + section);
            waitForElement(By.tagName("h1"));
            String screenshotPath = takeScreenshot(section);
            test.pass("Screenshot taken for section: " + section).addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            test.fail("Error while navigating to section '" + section + "': " + e.getMessage());
        }
    }

    // Implementing methods from SeleniumInterface
    @Override
    public void clickElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            element.click();
        } catch (Exception e) {
            test.fail("Failed to click element: " + e.getMessage());
        }
    }

    @Override
    public void waitForElement(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            test.fail("Failed to wait for element: " + e.getMessage());
        }
    }

    @Override
    public String takeScreenshot(String section) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "C:\\Users\\ADMIN\\Downloads\\Newfolders\\screenshots\\" + section.replace(" ", "_") + ".png";
        FileHandler.copy(screenshot, new File(screenshotPath));
        return screenshotPath;
    }

    // Additional interaction methods for specific sections
    private void handleProgrammingOverview() {
        try {
            clickElement(By.xpath("//div[text()='Prev']"));
            takeScreenshot("Programming_Overview_Prev");
        } catch (Exception e) {
            test.fail("Error while interacting with 'Programming Overview': " + e.getMessage());
        }
    }

    private void handleSyllabusSection() {
        try {
            clickElement(By.xpath("//mat-expansion-panel-header[contains(@aria-controls, 'cdk-accordion-child-0')]"));
            takeScreenshot("Syllabus_JS_Basics_Expanded");
        } catch (Exception e) {
            test.fail("Error while interacting with 'Syllabus' section: " + e.getMessage());
        }
    }

    private void handleCertificationDownload() {
        try {
            clickElement(By.xpath("//mat-icon[contains(text(), 'download')]"));
            takeScreenshot("Certification_Download");
        } catch (Exception e) {
            test.fail("Error while interacting with 'Certification' section: " + e.getMessage());
        }
    }

    private void handlePlacement() {
        try {
            clickElement(By.xpath("//button[text()='Enroll']"));
            takeScreenshot("Enroll_Button_Clicked");
        } catch (Exception e) {
            test.fail("Error while interacting with 'Placement' section: " + e.getMessage());
        }
    }

    private void handleReachUsSection() {
        try {
            clickElement(By.xpath("//a[text()='Reach Us']"));
            WebElement nextPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='modal-dialog']")));
            String screenshotPath = takeScreenshot("Reach_Us_Page");
            test.addScreenCaptureFromPath(screenshotPath);
            test.pass("Screenshot of 'Reach Us' page/modal taken successfully.");
        } catch (Exception e) {
            test.fail("Error while interacting with 'Reach Us' section: " + e.getMessage());
        }
    }

    // Helper method to close previous driver instance
    private void closePreviousDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush(); // Save the report
    }
}
