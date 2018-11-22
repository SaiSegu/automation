package com.java.automation.WebElementUtils;

import com.java.automation.Generic_Functions.ExecutionHelper;
import com.java.automation.Generic_Functions.ObjectRepoHandler;
import com.java.automation.Readers.PropertiesReader;
import com.java.automation.Reporter.Reporter;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UIActions {

    private final static Logger logger = Logger.getLogger(UIActions.class);
    private final WebDriver browser;
    private final ExtentTest currTest;
    private String mainWindowHandler;
    private WaitUtils waitUtils;

    public UIActions(WebDriver browser, ExtentTest testCaseLogger) {
        this.browser = browser;
        this.currTest = testCaseLogger;
        waitUtils = new WaitUtils(this.browser, currTest);
    }

    public boolean isElementExist(String element) {
        try {
            logger.info("IsElementExist called for " + element);
            return getWebElement(element).isDisplayed();
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    public boolean isElementExist(By element) {
        try {
            logger.info("IsElementExist called for " + element);
            return browser.findElement(element).isDisplayed();
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

    public String getTextOfElement(String element) {
        WebElement webElement = getWebElement(element);
        return getTextOfElement(webElement, element);
    }

    public String getTextOfElement(WebElement webElement, String element) {
        try {
            scrollToElement(webElement);
            return webElement.getText();
        } catch (Exception ee) {
            reportStepAndStopTest(ee, element, "GetTextOfElement");
            throw new RuntimeException(ee.getMessage());
        }
    }

    public WebElement getWebElement(String nameOfElementInOr) {
        try {
            logger.info("Fetching element from OR:" + nameOfElementInOr);
            return browser.findElement(ObjectRepoHandler.getElement(nameOfElementInOr));
        } catch (Exception ee) {
            logger.error(ee.getMessage());
            throw new RuntimeException(ee.getMessage());
        }
    }

    public List<WebElement> getWebElements(String nameOfElementInOr) {
        try {
            logger.info("Fetching element from OR:" + nameOfElementInOr);
            return browser.findElements(ObjectRepoHandler.getElement(nameOfElementInOr));
        } catch (Exception ee) {
            reportStepAndStopTest(ee, nameOfElementInOr, "GetWebElements");
            throw new RuntimeException();
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

    public void scrollToElement(WebElement element) {
        executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void click(String element) {
        try {
            WebElement currentElement = getWebElement(element);
            scrollToElement(currentElement);
            click(currentElement, element);
        } catch (Exception ee) {
            reportStepAndStopTest(ee, element, "Click");
        }
    }

    public void sendKeys(String element, String textToSet) {
        try {
            WebElement currentElement = getWebElement(element);
            scrollToElement(currentElement);
            sendKeys(currentElement, textToSet, element);
        } catch (Exception ee) {
            reportStepAndStopTest(ee, element, "sendKeys");
        }
    }

    private void reportStepAndStopTest(Exception ee, String element, String action) {
        ee.printStackTrace();
        logger.info("Exception while performing Action: " + action + " failed on " + element + " due to an exception. Exception is " + ee.getMessage());
        Reporter.logEvent(currTest, LogStatus.FAIL, action + " on " + element, action + " On " + element + " failed due to exception \n" + ee.getMessage(),
                getScreenShot());
        throw new RuntimeException(ee.getMessage());
    }

    public void clear(String element) {
        try {
            WebElement currentElement = getWebElement(element);
            clear(currentElement, element);
        } catch (Exception e) {
            reportStepAndStopTest(e, element, "clear");
        }
    }

    public void clear(WebElement currentElement, String element) {
        try {
            logger.info("clear on " + element);
            waitUtils.waitUntilElementVisible(currentElement);
            currentElement.clear();
            Reporter.logEvent(currTest, LogStatus.PASS, "clear", "clear On " + element + " Done Successfully",
                    getScreenShot());
        } catch (Exception ee) {
            ee.printStackTrace();
            Reporter.logEvent(currTest, LogStatus.FAIL, "clear", "clear On " + element + " failed due to exception \n" + ee.getMessage(),
                    getScreenShot());
            throw new RuntimeException(ee.getMessage());
        }
    }

    public void sendKeys(WebElement currentElement, String textToSet, String element) {
        try {
            logger.info("sendKeys on " + element);
            waitUtils.waitUntilElementVisible(currentElement);
            currentElement.sendKeys(textToSet);
            Reporter.logEvent(currTest, LogStatus.PASS, "Enter " + element + " with " + textToSet, "Entered " + textToSet + " in "+ element + " Done Successfully",
                    getScreenShot());
        } catch (Exception ee) {
            ee.printStackTrace();
            Reporter.logEvent(currTest, LogStatus.FAIL, "Enter " + element + " with " + textToSet, "Entering " + textToSet + " in "+ element + " failed due to exception \n" + ee.getMessage(),
                    getScreenShot());
            throw new RuntimeException(ee.getMessage());
        }
    }

    public List<String> getTextFromListOfWebElements(String elementName) {
        List<String> txtElements = new ArrayList<>();
        List<WebElement> webElements = getWebElements(elementName);
        for (int i = 0; i < webElements.size(); i++) {
            txtElements.add(getTextOfElement(webElements.get(i), i + "element from list"));
        }
        return txtElements;
    }

    public void clickAndWaitForElement(String element, String elementToWait) {
        click(element);
        waitUtils.waitUntilElementVisible(elementToWait);
    }

    public void click(WebElement currentElement, String element) {
        try {
            logger.info("Clicking on " + element);
            waitUtils.waitUntilElementVisible(currentElement);
            waitUtils.waitUntilElementClickable(currentElement);
            currentElement.click();
            Reporter.logEvent(currTest, LogStatus.PASS, "Click On " + element, "Clicked On " + element + "  Successfully",
                    getScreenShot());
        } catch (Exception ee) {
            ee.printStackTrace();
            Reporter.logEvent(currTest, LogStatus.FAIL, "Click On " + element, "Clicking On " + element + " failed due to exception \n" + ee.getMessage(),
                    getScreenShot());
            throw new RuntimeException(ee.getMessage());
        }
    }
    
    public void clickBack() {
        try {
            logger.info("Clicking browser back button "  );
            browser.navigate().back();
            Reporter.logEvent(currTest, LogStatus.PASS, "Click On browser back button.", "Clicked On browser back button Successfully.");
        } catch (Exception ee) {
            ee.printStackTrace();
            Reporter.logEvent(currTest, LogStatus.FAIL, "Cannot click on browser back button.", "Clicking On browser back button failed due to exception \n" + ee.getMessage(),
                    getScreenShot());
            throw new RuntimeException(ee.getMessage());
        }
    }

    public void switchToWindow(String title) {
        mainWindowHandler = browser.getWindowHandle();
        for (String winHandle : browser.getWindowHandles()) {
            browser.switchTo().window(winHandle);
            if (browser.getTitle().equalsIgnoreCase(title)) {
                Reporter.logEvent(currTest, LogStatus.PASS, "Switch to window", "Switched to window with title:" + title,
                        getScreenShot());
                return;
            }
        }
    }

    public void closeCurrentWindowAndReturnToParent() {
        browser.close();
        browser.switchTo().window(mainWindowHandler);
    }

    public String getCurrentPageURL() {
        return browser.getCurrentUrl();
    }

    public int openWindows() {
        return browser.getWindowHandles().size();
    }

    public void navigateToPage(String url, String containerElement) {
        browser.get(PropertiesReader.getProperty("BASE_URL") + url);
        waitUtils.waitUntilElementVisible(containerElement);
        Reporter.logEvent(currTest, LogStatus.PASS, "Navigate", "Navigated to  " + url, getScreenShot());
    }

    public void navigateToFullURL(String url, String containerElement) {
        browser.get(url);
        waitUtils.waitUntilElementVisible(containerElement);
        Reporter.logEvent(currTest, LogStatus.PASS, "Navigate", "Navigated to  " + url, getScreenShot());
    }

    public List<String> getAllElementsText(String identifier) {
        By currentElement = waitUtils.waitUntilElementsVisible(identifier);
        List<WebElement> lst = browser.findElements(currentElement);
        List<String> textList = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            textList.add(lst.get(i).getText());
        }
        return textList;
    }

    public boolean isElementNotExist(String element) {
        try {
            logger.info("IsElementExist called for " + element);
            return !getWebElement(element).isDisplayed();
        } catch (Exception ee) {
            return true;
        }
    }

    public void clearAndSendKeys(String element, String textToSet) {
        clear(element);
        sendKeys(element, textToSet);
        logger.info("Entering " + element + " with " + textToSet);
    }

    public void selectValueFromDropDown(String dropDownElement, String valueToSelect) {
        try {
            WebElement element = getWebElement(dropDownElement);
            Select selectList = new Select(element);
            selectList.selectByVisibleText(valueToSelect);
            logger.info("Selecting value from " + dropDownElement + " as " + valueToSelect);
            Reporter.logEvent(currTest, LogStatus.PASS, "Selecting Value from dropdown " + element, "Selecting value " + valueToSelect + " from " + element + " Done Successfully",
                    getScreenShot());
        } catch (Exception ee) {
            reportStepAndStopTest(ee, dropDownElement, "SelectValue from dropdown");
        }


    }

    public void click(String identifier, int location) {
        try {
            click(getWebElements(identifier).get(location - 1), identifier + ": Location:" + location);
        } catch (Exception ee) {
            reportStepAndStopTest(ee, identifier, "Clicking on specific location");
            throw new RuntimeException(ee.getMessage());
        }
    }

    public void executeScript(String javascript, Object arguments) {
        ((JavascriptExecutor) browser).executeScript(javascript, arguments);
    }

    public void executeScript(String javascript) {
        ((JavascriptExecutor) browser).executeScript(javascript);
    }

    public boolean isElementDisplayed(String element) {
        try {
            browser.findElement(ObjectRepoHandler.getElement(element));
            logger.info("isElementDisplayed called for " + element + ": Status is:true");
            return true;
        } catch (NoSuchElementException e) {
            logger.info("isElementDisplayed called for " + element + ": Status is:false");
            return false;
        }
    }

    public String getTextOfInputElement(String element) {
        WebElement webElement = getWebElement(element);
        return getAttributeOfElement(webElement, element, "value");
    }

    private String getAttributeOfElement(WebElement webElement, String element, String attribute) {
        try {
            scrollToElement(webElement);
            return webElement.getAttribute(attribute);
        } catch (Exception ee) {
            reportStepAndStopTest(ee, element, "Get Attribute " + attribute);
            throw new RuntimeException(ee.getMessage());
        }
    }

    public String getSelectItemFromDropDown(String element) {
        try {
            WebElement webElement = getWebElement(element);
            return getSelectItemFromDropDown(webElement);
        } catch (Exception ee) {
            reportStepAndStopTest(ee, element, "getSelectItemFromDropDown");
            throw new RuntimeException(ee.getMessage());
        }
    }

    private String getSelectItemFromDropDown(WebElement webElement) {
        Select select = new Select(webElement);
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }
}
