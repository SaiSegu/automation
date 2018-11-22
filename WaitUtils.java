package com.java.automation.WebElementUtils;

import com.java.automation.Generic_Functions.ExecutionHelper;
import com.java.automation.Generic_Functions.ObjectRepoHandler;
import com.java.automation.Reporter.Reporter;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class WaitUtils {
    final static Logger logger = Logger.getLogger(WaitUtils.class);
    private final WebDriver browser;
    private final ExtentTest currentTest;

    public WaitUtils(WebDriver browser, ExtentTest testCaseLogger) {
        this.browser = browser;
        currentTest = testCaseLogger;
    }

    public void waitUntilElementVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(browser, 45);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void waitUntilElementVisible(long timeInSec, WebElement element) {
        try {
            new WebDriverWait(browser, timeInSec).until(ExpectedConditions.visibilityOf(element));
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void waitUntilElementVisible(String containerElement) {
        By currentElement = ObjectRepoHandler.getElement(containerElement);
        try {
            WebElement webElement = browser.findElement(currentElement);
            waitUntilElementVisible(webElement);
        } catch (Exception ee) {
            ee.printStackTrace();
            Reporter.logEvent(currentTest, LogStatus.ERROR, "Waiting for Element: " + containerElement,
                    getScreenShot());
            logger.error("Exception while waiting for element");
            throw new RuntimeException(ee.getMessage());
        }
    }

    public void waitUntilElementClickable(WebElement currentElement) {
        WebDriverWait wait = new WebDriverWait(browser, 30);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(currentElement));
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
    
    public void waitUntilElementClickable_staleHandling(String containerElement) {
    	By currentElement = ObjectRepoHandler.getElement(containerElement);
        int attempts=0;
        while (attempts < 2){
        	try {
                WebElement webElement = browser.findElement(currentElement);
                waitUntilElementVisible(webElement);
            } catch (Exception ee) {
                ee.printStackTrace();
                Reporter.logEvent(currentTest, LogStatus.ERROR, "Waiting for Element: " + containerElement,
                        getScreenShot());
                logger.error("Exception while waiting for element");
                throw new RuntimeException(ee.getMessage());
            }
        	attempts ++;
        }
    }

    public void waitUntilElementVisible(By currentElement) {
        try {
            new WebDriverWait(browser, 30).until(ExpectedConditions.presenceOfElementLocated(currentElement));
        } catch (Exception ee) {
            ee.printStackTrace();
            Reporter.logEvent(currentTest, LogStatus.ERROR, "waitUntilElementVisible", "Error while waiting for element " + currentElement);
            logger.error("Exception while waiting for element");
            throw new RuntimeException(ee.getMessage());
        }
    }

    public By waitUntilElementsVisible(String identifier) {
        By currentElement = ObjectRepoHandler.getElement(identifier);
        try {
            new WebDriverWait(browser, 40).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(currentElement));
            return currentElement;
        } catch (Exception e) {
            logger.info("wait failed with exception");
            return currentElement;
        }
    }

    public By waitUntilElementVisible(String identifier, int timeout) {
        By currentElement = ObjectRepoHandler.getElement(identifier);
        try {
            new WebDriverWait(browser, timeout).until(ExpectedConditions.visibilityOfElementLocated(currentElement));
            return currentElement;
        } catch (Exception e) {
            logger.info("wait failed with exception while clicking on "+identifier);
            return currentElement;
        }
    }

    public String getScreenShot() {
        String fileLocation = ExecutionHelper.currentExecDir() + "/target/" + Reporter.currentExecFolder + "/" + ExecutionHelper.getDateTimeStamp() + ".png";
        File src = ((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(fileLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLocation;
    }
}
