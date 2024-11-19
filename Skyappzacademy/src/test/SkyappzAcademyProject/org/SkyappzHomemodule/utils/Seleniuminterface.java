package org.SkyappzHomemodule.utils;

import java.io.IOException;
import org.openqa.selenium.WebElement;

/**
 * Interface for common Selenium WebDriver actions such as taking screenshots 
 * and scrolling to elements on the web page.
 * <p>
 * This interface provides method signatures for actions that can be implemented
 * in various test classes. Any class that interacts with Selenium WebDriver 
 * and requires these functionalities can implement this interface.
 * </p>
 * 
 * <p>
 * Example usage:
 * <pre>
 * public class WebDriverActions implements Seleniuminterface {
 *     // Implement the methods here
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
     * Captures a screenshot of the current page in the browser.
     * <p>
     * The screenshot is saved in the specified location with the provided
     * file name. This method will throw an IOException if there is an issue
     * with saving the file.
     * </p>
     *
     * @param fileName The name to use for the screenshot file. The file will be
     *                 saved with this name in the designated folder.
     * @return The path where the screenshot is saved.
     * @throws IOException If there is an error during the screenshot capture or 
     *                     saving process.
     */
    String takeScreenshot(String fileName) throws IOException;

    /**
     * Scrolls the page until the specified element is in view.
     * <p>
     * This method ensures that the given element is scrolled into view if
     * it's not already visible on the screen. This can be useful in scenarios
     * where elements are located far down the page or in dynamic content sections.
     * </p>
     *
     * @param element The WebElement that you want to scroll into view.
     *                The page will be scrolled to this element.
     */
    void scrollToElement(WebElement element);
}
