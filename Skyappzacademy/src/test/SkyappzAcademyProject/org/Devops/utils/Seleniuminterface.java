package org.Devops.utils;

import java.io.IOException;

import org.openqa.selenium.By;

/**
 * Interface to define standard selenium interactions that can be implemented in your test class.
 */
public interface Seleniuminterface {

    /**
     * Clicks on the element defined by the given locator.
     * @auther Jayakumar
     * @param locator The locator of the element to click.
     */
    void clickElement(By locator);

    /**
     * Waits for the element defined by the given locator to be visible.
     * 
     * @param locator The locator of the element to wait for.
     */
    void waitForElement(By locator);

    /**
     * Takes a screenshot and returns the file path.
     * 
     * @param section The section name for naming the screenshot file.
     * @return The path of the screenshot.
     * @throws IOException If an error occurs during file handling.
     */
    String takeScreenshot(String section) throws IOException;
}
