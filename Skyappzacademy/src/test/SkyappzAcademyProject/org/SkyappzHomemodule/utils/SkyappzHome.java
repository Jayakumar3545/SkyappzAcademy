package org.SkyappzHomemodule.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class SkyappzHome implements Seleniuminterface {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setUp() {
        // Initialize ExtentReports
        extent = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("C:\\Users\\ADMIN\\Downloads\\Newfolders\\Skyappzacademy.html");
        extent.attachReporter(sparkReporter);

        // Set up WebDriver for Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\Downloads\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to the website
        driver.get("https://skyappzacademy.com/");
        test = extent.createTest("Browser Setup").log(Status.INFO, "Opened Skyappz Academy Website");
    }

    @Test(priority = 1)
    public void testSkyappzAcademyDataScienceSubModules() throws IOException, InterruptedException {
        test = extent.createTest("Navigate and Test Skyappz Academy Data Science Submodules");

        try {
            // Close the popup if it appears
            try {
                WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='close']")));
                popup.click();
                test.info("Popup closed successfully.");
            } catch (TimeoutException e) {
                test.info("No popup appeared.");
            }

            // Click on "Skyappz Academy" link
            WebElement skyappzAcademy = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='mat-menu-trigger']")));
            skyappzAcademy.click();
            test.info("Clicked on Skyappz Academy link.");

            // Click on "Data Science" link
            WebElement dataScience = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Data Science']")));
            dataScience.click();
            test.info("Clicked on Data Science link.");

            // Scroll to footer and ensure it is visible
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//footer")));
            test.info("Footer is visible.");

            // Take screenshot of footer
            String footerScreenshotPath = takeScreenshot("footer");
            test.addScreenCaptureFromPath(footerScreenshotPath);
            test.log(Status.PASS, "Screenshot of footer taken successfully.");

            // Find submodules in the footer
            List<WebElement> submodules = driver.findElements(By.xpath(
                "//footer//a[contains(text(), 'Full Stack Development') or " +
                "contains(text(), 'Data Science') or contains(text(), 'Cloud Computing') or " +
                "contains(text(), 'Front End Development') or " +
                "contains(text(), 'Back End Development') or contains(text(), 'Digital Marketing')]"));

            // Click each submodule and take screenshots
            for (WebElement submodule : submodules) {
                try {
                    // Scroll to the submodule to ensure it's in view
                    scrollToElement(submodule);

                    // Wait until the submodule is clickable
                    wait.until(ExpectedConditions.elementToBeClickable(submodule)).click();
                    String submoduleName = submodule.getText().trim();

                    // Take screenshot of submodule page
                    String screenshotPath = takeScreenshot(submoduleName);
                    test.addScreenCaptureFromPath(screenshotPath);
                    test.log(Status.PASS, "Screenshot of " + submoduleName + " taken successfully.");
                } catch (Exception e) {
                    test.fail("Failed to click and capture screenshot for submodule: " + e.getMessage());
                }
            }

            // Check and click the additional submodules (Python, React Js, etc.)
            List<WebElement> submodules1 = driver.findElements(By.xpath(
                "//footer//a[contains(text(), 'Python') or contains(text(), 'React Js') or " +
                "contains(text(), 'Mongo DB') or contains(text(), 'Angular') or contains(text(), 'Java Script') or " +
                "contains(text(), 'Vue Js') or contains(text(), 'PHP')]"));

            for (WebElement submodule1 : submodules1) {
                try {
                    // Scroll to the submodule to ensure it's in view
                    scrollToElement(submodule1);

                    // Wait until the submodule is clickable
                    wait.until(ExpectedConditions.elementToBeClickable(submodule1)).click();
                    String submoduleName1 = submodule1.getText().trim();

                    // Take screenshot of submodule page
                    String screenshotPath1 = takeScreenshot(submoduleName1);
                    test.addScreenCaptureFromPath(screenshotPath1);
                    test.log(Status.PASS, "Screenshot of " + submoduleName1 + " taken successfully.");
                    test.log(Status.FAIL,"Not clicking the program");

                } catch (Exception e) {
                    // Log failure for each submodule (Python, React JS, etc.)
                    test.fail("Failed to click and capture screenshot for submodule: " + submodule1.getText());
                    test.log(Status.FAIL, "Test failed for submodule: " + submodule1.getText() + ". Error: " + e.getMessage());
                }
            }

            // Optionally wait before clicking the next submodule
            Thread.sleep(2000); // Adjust as needed

        } catch (Exception e) {
            test.fail("Failed during submodule testing: " + e.getMessage());
        }
    }

    // Implementation of the takeScreenshot method from Seleniuminterface
    @Override
    public String takeScreenshot(String fileName) throws IOException {
        File screenshotDir = new File("C:\\Users\\ADMIN\\eclipse-workspace\\SeleniumProject\\screenshots\\");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = screenshotDir + "\\" + fileName + ".png";
        FileHandler.copy(screenshot, new File(screenshotPath));
        System.out.println("Screenshot taken: " + fileName);
        return screenshotPath;
    }

    // Implementation of the scrollToElement method from Seleniuminterface
    @Override
    public void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
        openReportInChrome(); // Open the ExtentReports HTML in Chrome
    }

    // Open the ExtentReports HTML file in Chrome browser
    public void openReportInChrome() {
        try {
            String reportPath = "C:\\Users\\ADMIN\\Downloads\\Newfolders\\Skyappzacademy.html";
            Runtime.getRuntime().exec(new String[] {
                "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", reportPath
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
