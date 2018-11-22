package com.test.automation.hooks;

import com.java.automation.BrowserFactory.Browser;
import com.java.automation.Generic_Functions.ObjectRepoHandler;
import com.java.automation.Readers.PropertiesReader;
import com.java.automation.Readers.TestData;
import com.java.automation.Reporter.Reporter;
import com.java.automation.Verify.Verify;
import com.java.automation.WebElementUtils.UIActions;
import com.java.automation.WebElementUtils.WaitUtils;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;

public class BaseHook {

    public WebDriver browser;
    protected ExtentTest currentTestCase;
    protected ExtentTest parentTest;
    final static Logger logger = Logger.getLogger(BaseHook.class);
    public Verify verify;
    public UIActions uiActions;
    public WaitUtils waitUtils;
    public TestData testData;

    @BeforeSuite
    public void suiteSetup(ITestContext iSuite) {
        PropertiesReader.init();
        ObjectRepoHandler.init();
        Reporter.startReporter(iSuite.getSuite().getName());
    }

    @BeforeTest
    public void beforeTestCase(ITestContext testArgs) {
        browser = Browser.getBrowser();
        browser.manage().window().maximize();
        parentTest = Reporter.startParentTest(testArgs.getCurrentXmlTest().getName());
    }

    @BeforeMethod
    public void startTest(Method method) {
        Test test = method.getAnnotation(Test.class);

        currentTestCase = Reporter.startTestCase(test.description());
        verify = new Verify(browser, currentTestCase);
        uiActions = new UIActions(browser, currentTestCase);
        waitUtils = new WaitUtils(browser, currentTestCase);
        testData = new TestData();

        try {
            testData.init(method.getName());
        } catch (RuntimeException ee) {
            Reporter.logEvent(currentTestCase, LogStatus.INFO, "Test Data Warning", "Test Data not found for " + method.getName(),
                    uiActions.getScreenShot());
        }
    }

    @AfterMethod
    public void endTest(ITestContext testArgs) {
        Reporter.endTestCase(currentTestCase);
        Reporter.appendTestCase(parentTest, currentTestCase);
    }

    @AfterTest
    public void afterTestCase() {
        Reporter.endParentTestCase(parentTest);
        browser.quit();
    }

    @AfterSuite
    public void tearDown(XmlTest iSuite) {
        browser.quit();
        ObjectRepoHandler.flushOR();
        Reporter.endReport();
        logger.info("Tear down completed");
    }
}
