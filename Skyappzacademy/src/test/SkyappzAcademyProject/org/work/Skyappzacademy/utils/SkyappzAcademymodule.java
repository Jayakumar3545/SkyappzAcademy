package org.work.Skyappzacademy.utils;

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

public class SkyappzAcademymodule implements Seleniuminterface {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setUp() {
        // Initialize ExtentReports
        extent = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("C:\\Users\\ADMIN\\Downloads\\Newfolders\\Skyappz.html");
        extent.attachReporter(sparkReporter);

        // Set up WebDriver for Chrome
        setupWebDriver();
        test = extent.createTest("Browser Setup").log(Status.INFO, "Opened Skyappz Academy Website");
    }

    @Test(priority = 1)
    public void testSkyappzModules() throws IOException {
        test = extent.createTest("Navigate and Test Modules");

        try {
            // 1. Close the popup if it appears
            handlePopup();

            // 2. Navigate to Home, scroll, and take screenshot
            navigateToHome();

            // 3. Click on Success Story, handle iframe YouTube, and take screenshot
            navigateToSuccessStory();

            // 4. Navigate to Franchise, take screenshot
            navigateToFranchise();

            // 5. Navigate to Reach Us, take screenshot
            navigateToReachUs();

        } catch (Exception e) {
            test.fail("Failed during module testing: " + e.getMessage());
        }
    }

    @Override
    public void setupWebDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\Downloads\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to the website
        openWebsite("https://skyappzacademy.com/");
    }

    @Override
    public void openWebsite(String url) {
        driver.get(url);
    }

    @Override
    public void waitForElement(WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    @Override
    public void clickElement(WebElement element) {
        waitForElement(element, 10);
        element.click();
    }

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

    @Override
    public void handlePopup() {
        try {
            WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='close']")));
            popup.click();
            test.info("Popup closed successfully.");
        } catch (TimeoutException e) {
            test.info("No popup appeared.");
        }
    }

    @Override
    public boolean isVideoClosed() {
        try {
            WebElement playButton = driver.findElement(By.xpath("//button[@aria-label='Play']"));
            return !playButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return true; // If play button is not found, it means the player has closed
        }
    }

    private void navigateToHome() throws IOException {
        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Home']")));
        home.click();
        test.info("Clicked on Home link.");

        js.executeScript("window.scrollBy(0, 2000);");
        String screenshotPath = takeScreenshot("HomePage");
        test.addScreenCaptureFromPath(screenshotPath);
        test.log(Status.PASS, "Screenshot of Home page taken successfully.");
    }

    private void navigateToSuccessStory() throws IOException {
        WebElement successStory = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Success Story']")));
        successStory.click();
        test.info("Clicked on Success Story link.");
        
        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@src,'youtube.com/embed/V8Ub1IBqZkQ')]")));
            driver.switchTo().frame(iframe);

            WebElement playButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Play']")));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", playButton);

            if (playButton.isDisplayed() && playButton.isEnabled()) {
                try {
                    playButton.click();
                    test.info("Played YouTube video.");
                    Thread.sleep(40000);  // Adjust based on how long the video takes to start

                    if (isVideoClosed()) {
                        js.executeScript("arguments[0].click();", playButton);
                        test.info("Played YouTube video after simulating a second click.");
                    }

                } catch (ElementClickInterceptedException e) {
                    js.executeScript("arguments[0].click();", playButton);
                    test.info("Played YouTube video using JavaScript.");
                }
            } else {
                test.fail("Play button is not clickable.");
            }

            driver.switchTo().defaultContent();
            String successStoryScreenshotPath = takeScreenshot("SuccessStory");
            test.addScreenCaptureFromPath(successStoryScreenshotPath);
            test.log(Status.PASS, "Screenshot of Success Story page with video taken.");
        } catch (Exception e) {
            test.fail("Failed to interact with the YouTube video iframe: " + e.getMessage());
        }
    }

    private void navigateToFranchise() throws IOException {
        WebElement franchise = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Franchise']")));
        franchise.click();
        test.info("Clicked on Franchise link.");

        String franchiseScreenshotPath = takeScreenshot("Franchise");
        test.addScreenCaptureFromPath(franchiseScreenshotPath);
        test.log(Status.PASS, "Screenshot of Franchise page taken.");
    }

    private void navigateToReachUs() throws IOException {
        WebElement reachUs = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Reach Us']")));
        js.executeScript("arguments[0].scrollIntoView(true);", reachUs);

        wait.until(ExpectedConditions.elementToBeClickable(reachUs)).click();
        test.info("Clicked on Reach Us link.");

        String reachUsScreenshotPath = takeScreenshot("ReachUs");
        test.addScreenCaptureFromPath(reachUsScreenshotPath);
        test.log(Status.PASS, "Screenshot of Reach Us page taken.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
        openReportInChrome(); // Open the ExtentReports HTML in Chrome
    }

    public void openReportInChrome() {
        try {
            String reportPath = "C:\\Users\\ADMIN\\Downloads\\Newfolders\\Skyappz.html";
            Runtime.getRuntime().exec(new String[] {
                "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                reportPath
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
