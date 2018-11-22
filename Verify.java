package com.java.automation.Verify;

import com.java.automation.Reporter.Reporter;
import com.java.automation.WebElementUtils.UIActions;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class Verify {

    private final static Logger log4jLogger = Logger.getLogger(Verify.class);
    private final ExtentTest tcLogger;
    private UIActions uiActions;

    public Verify(WebDriver browser, ExtentTest testCaseLogger) {
        this.tcLogger = testCaseLogger;
        uiActions = new UIActions(browser, tcLogger);
    }

    public void validateTrue(boolean condition, String description) {
        if (condition) {
            log4jLogger.info("Verify : validate True : Successfully completed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify: elementExists: Success", description, uiActions.getScreenShot());
        } else {
            log4jLogger.info("Verify: validateTrue:Failed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verify: validateTrue: Failed", description, uiActions.getScreenShot());
            throw new RuntimeException("Verification failed: validateTrue:");
        }
    }

    public void elementExists(String element, String description) {
        boolean elementExist = uiActions.isElementExist(element);
        if (elementExist) {
            log4jLogger.info("Verified that " +element + " exists Successfully: " + description);
            Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify that" + element+ " exists Successfully", description, uiActions.getScreenShot());
        } else {
            log4jLogger.info("Verified that " +element + " doesnt Exists: Failed " + description);
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verification Failed for existance of " + element, description, uiActions.getScreenShot());
            throw new RuntimeException("Verification failed: Element doesnt Exist:" + element);
        }
    }
    
    public void textEqual(String actual, String expected, String description) {
        if (actual.equals(expected)) {
            log4jLogger.info("Verify if equal:Successfully completed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify that expected : "+expected+" and actual : "+actual+" are equal", description, uiActions.getScreenShot());
        } else {
            log4jLogger.info("Verify: equal:Failed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verify: equal: Failed", "Expected " + expected + "  Actual:" + actual + " \t" + description, uiActions.getScreenShot());
            throw new RuntimeException("Verification failed: equal: Expected was " + expected + " actual was " + actual);
        }
    }
	   public void partialEqual(String actual, String expected, String description) {
        if (actual.contains(expected)) {
            log4jLogger.info("Verify if equal:Successfully completed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify that expected : "+expected+" and actual : "+actual+" are equal", description, uiActions.getScreenShot());
        } else {
            log4jLogger.info("Verify: equal:Failed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verify: equal: Failed", "Expected " + expected + "  Actual:" + actual + " \t" + description, uiActions.getScreenShot());
            throw new RuntimeException("Verification failed: equal: Expected was " + expected + " actual was " + actual);
        }
    }

    public void equal(int expected, int actual, String description) {
        if (expected == actual) {
            log4jLogger.info("Verify that expected : "+expected+" and actual: "+actual+" are equal -->"+description);
            Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify that expected : "+expected+" and actual: "+actual+" are equal", description, uiActions.getScreenShot());
        } else {
            log4jLogger.info("Verification Failed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verifying failed for expected : "+expected+" and actual: "+actual+" are equal" + expected + "  Actual:" + actual + " \t" + description, uiActions.getScreenShot());
            throw new RuntimeException("Verification failed : Expected was " + expected + " actual was " + actual);
        }
    }

    public void textOfElement(String element, String expectedText, String description) {
        try {
            String uiText = uiActions.getTextOfElement(element).replace("‑", "-");
            if (uiText.equals(expectedText)) {
                log4jLogger.info("Verify that "+element+" has the following text : \n "+ expectedText+":" + description);
                Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify that "+element+" has "+ expectedText , description,
                        uiActions.getScreenShot());
            } else {
                log4jLogger.info("Verification Failed : " + element + " doesnot contains the Expected text : " + description);
                Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verification Failed : " + element ,
                        " it doesnot contains the Expected text : " + expectedText + " Actual text on the site is :" + uiText,
                        uiActions.getScreenShot());
                throw new RuntimeException("Verification failed: textOfElement:" + element);
            }
        } catch (Exception ee) {
            Reporter.logEvent(tcLogger, LogStatus.ERROR, ee.getMessage() + " While getting text", uiActions.getScreenShot());
            throw new RuntimeException(ee.getMessage());
        }
    }

    public void validateTextFromListOfElement(String elements, List<String> errorMessages, String description) {

        List<String> txtElements = uiActions.getTextFromListOfWebElements(elements);
        if (txtElements.size() == errorMessages.size()) {
            Reporter.logEvent(tcLogger, LogStatus.PASS, "validateTextFromListOfElement: Size compare:", "List comparision",
                    "Size of expected list and actual list validation. Expected:" + errorMessages.size()
                            + "\n But actual:" + txtElements.size());
        } else {
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "validateTextFromListOfElement: Size compare:", "List comparision:"
                    + "Size of expected list and actual list validation. Expected:" + errorMessages.size()
                    + "\n But actual:" + txtElements.size(), uiActions.getScreenShot());
        }

        if (txtElements.containsAll(errorMessages)) {
            Reporter.logEvent(tcLogger, LogStatus.PASS, "validateTextFromListOfElement: List compare:",
                    description + " expected List:" + errorMessages.toString() + "Actual:" + txtElements.toString(), null);
        } else {
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "validateTextFromListOfElement: List compare:",
                    description + " expected List:" + errorMessages.toString() + "Actual:" + txtElements.toString(), null);
        }
    }

    public void textOfElementContains(String element, String expectedText, String description) {
        String uiText = uiActions.getTextOfElement(element);
        if (uiText.contains(expectedText)) {
            log4jLogger.info("Verified Successfully that" + element + " has " + description);
            Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify" + element, description, uiActions.getScreenShot());
        } else {
            log4jLogger.info("Verify: textOfElementContains:Failed for " + element + ":" + description);
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verifiying that "+ element + "contains the mentioned text Failed", "Expected:" + expectedText + "  Actual:" + uiText + "  \t" + description,
                    uiActions.getScreenShot());
            throw new RuntimeException("Verification failed, Element contains:" + element);
        }
    }

    public void elementDoesNotExists(String element, String description) {
        boolean elementDoesNotExist = uiActions.isElementNotExist(element);
        if (elementDoesNotExist) {
            log4jLogger.info("Verified that element Does Not Exists:Successfully completed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify that " + element + " does not exists: Success", description, uiActions.getScreenShot());
        } else {
            log4jLogger.info("Verify:elementDoesNotExists:Failed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verification Failed: "+element +" exists", description, uiActions.getScreenShot());
            throw new RuntimeException("Verification failed: elementExists:" + element);
        }
    }

    public void equal(List<String> actualList, List<String> expectedList, String description) {
        boolean compareStatus = actualList.containsAll(expectedList) && expectedList.containsAll(actualList);
        if (compareStatus) {
            log4jLogger.info("Verify:equal List:Successfully completed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify:equal List: Success", description);
        } else {
            log4jLogger.info("Verify:equal List:Failed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verify:equal List: Failed", "Lists=> Expected:" + expectedList.toString() + ": Actual:" + actualList.toString() + description, uiActions.getScreenShot());
            throw new RuntimeException("List comparision completed Expected:" + expectedList.toString() + ": Actual:" + actualList.toString());
        }
    }

    public void elementExistWithText(String elementText) {
        uiActions.isElementExist(By.xpath(".//*[text()'" + elementText + "']"));
    }

    public void textOfInputElement(String element, String expectedText, String description) {
        try {
            String uiText = uiActions.getTextOfInputElement(element);
            if (uiText.equals(expectedText)) {
                log4jLogger.info("Verify:Value of input:Successfully completed for " + element + ":" + description);
                Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify text for: " + element, description,
                        uiActions.getScreenShot());
            } else {
                log4jLogger.info("Verify: value of input:Failed for " + element + ":" + description);
                Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verification Failed for text of " + element,
                        ":Expected " + expectedText + ":Actual:" + uiText,
                        uiActions.getScreenShot());
                throw new RuntimeException("Verification failed: textOfElement:" + element);
            }
        } catch (Exception ee) {
            Reporter.logEvent(tcLogger, LogStatus.ERROR, ee.getMessage() + " While getting text", uiActions.getScreenShot());
            throw new RuntimeException(ee.getMessage());
        }
    }
    
    public void windowsCount(int expected, int actual,String open_close, String description) {
        if (expected == actual) {
            log4jLogger.info("Verify that expected windows : "+ expected +" and actual windows : "+actual+" are equal -->"+description);
            Reporter.logEvent(tcLogger, LogStatus.PASS, "Verify that "+ open_close, description, uiActions.getScreenShot());
        } else {
            log4jLogger.info("Verification Failed:" + description);
            Reporter.logEvent(tcLogger, LogStatus.FAIL, "Verification failed for " + description, uiActions.getScreenShot());
            throw new RuntimeException("Verification failed : Expected windows were " + expected + " actual is " + actual);
        }
    }

    public void selectedItemOfDropdown(String element, String expectedText, String description) {
        try {
            String actual = uiActions.getSelectItemFromDropDown(element);
            if (actual.equals(expectedText)) {
                log4jLogger.info("Selecting Value From Dropdown : Success for " + element + ":" + description);
                Reporter.logEvent(tcLogger, LogStatus.PASS, "Selecting Value From Dropdown : Success for " + element, description,
                        uiActions.getScreenShot());
            } else {
                log4jLogger.info("Selecting Value From Dropdown : Failed for " + element + ":" + description);
                Reporter.logEvent(tcLogger, LogStatus.FAIL, "Selecting Value From Dropdown : Failed for " + element,
                        ":Expected " + expectedText + ":Actual:" + actual,
                        uiActions.getScreenShot());
                throw new RuntimeException("Failed : Selecting Value From DropDown:" + element);
            }
        } catch (Exception ee) {
            Reporter.logEvent(tcLogger, LogStatus.ERROR, ee.getMessage() + " While selecting value from DropDown", uiActions.getScreenShot());
            throw new RuntimeException(ee.getMessage());
        }
    }
}