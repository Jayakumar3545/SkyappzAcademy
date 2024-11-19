package org.work.Skyappzacademy.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.io.IOException;

/**
 * Seleniuminterface defines common actions for interacting with a Selenium WebDriver 
 * in test automation. These actions include setup of WebDriver, opening websites, 
 * interacting with page elements, handling popups, taking screenshots, and checking 
 * if a video has been closed.
 * 
 * <p>
 * This interface serves as a contract for any classes that require basic Selenium 
 * WebDriver operations. Implementing this interface allows for uniformity in 
 * automation tasks and easy reuse across different test scenarios.
 * </p>
 * 
 * <p>
 * Example usage:
 * @author Jayakumar
 * <pre>
 * public class WebDriverActions implements Seleniuminterface {
 *     // Implement the methods from the interface
 *     public void setupWebDriver() {
 *         // WebDriver setup code
 *     }
 *     public void openWebsite(String url) {
 *         // Open website code
 *     }
 *     // Implement all methods here...
 * }
 * </pre>
 * </p>
 * 
 * @author Jayakumar
 * @version 1.0
 * @since 2024-11-18
 */
public interface Seleniuminterface {

    /**
     * Sets up the WebDriver and browser configurations. 
     * This method initializes the WebDriver instance, sets properties, 
     * configures the browser (e.g., window size), and applies any necessary timeouts.
     * 
     * @since 2024-11-18
     */
    void setupWebDriver();
    
    /**
     * Opens the specified URL in the browser. 
     * This method directs the browser to navigate to the given website address.
     * 
     * @param url The URL of the website to open.
     * @since 2024-11-18
     */
    void openWebsite(String url);
    
    /**
     * Waits for the specified web element to be visible and interactable. 
     * The method ensures that the element is in a state that can be interacted with (e.g., clicked, typed into).
     * It waits until the element becomes visible and ready within the given timeout.
     * 
     * @param element The WebElement to wait for.
     * @param timeoutInSeconds The maximum number of seconds to wait for the element.
     * @since 2024-11-18
     */
    void waitForElement(WebElement element, int timeoutInSeconds);
    
    /**
     * Clicks on the specified web element. 
     * This method triggers a click action on the element, such as a button, link, or other clickable items.
     * It ensures the element is visible and interactable before attempting the click.
     * 
     * @param element The WebElement to be clicked.
     * @since 2024-11-18
     */
    void clickElement(WebElement element);
    
    /**
     * Takes a screenshot of the current page and returns the file path.
     * This method captures a screenshot and saves it to a specific location in the file system.
     * It returns the path to the saved screenshot file for further use, such as logging or reporting.
     * 
     * @param fileName The name of the file where the screenshot will be saved.
     * @return The path of the saved screenshot.
     * @throws IOException If an error occurs during the screenshot capture process.
     * @since 2024-11-18
     */
    String takeScreenshot(String fileName) throws IOException;
    
    /**
     * Handles popups, such as closing alerts, modals, or any other types of popup elements.
     * This method can be used to close browser alerts or modals that may appear during the test run.
     * 
     * @since 2024-11-18
     */
    void handlePopup();
    
    /**
     * Checks if a video (typically in an iframe) has been closed.
     * This method is useful for automating interactions where videos need to be closed 
     * before continuing with the test steps, ensuring the video is no longer present in the UI.
     * 
     * @return true if the video (iframe) has been closed, false otherwise.
     * @since 2024-11-18
     */
    boolean isVideoClosed();
}
