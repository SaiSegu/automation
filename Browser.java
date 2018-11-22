package com.java.automation.BrowserFactory;

import com.java.automation.Readers.PropertiesReader;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class Browser {

    public static String mainWindowHandler;
    final static Logger logger = Logger.getLogger(Browser.class);

    private static WebDriver createBrowser() {
        if (System.getProperty("BROWSER_TYPE") == null) {
            logger.warn("BROWSER_TYPE was not specified, Select Chrome as default browser");
            System.setProperty("BROWSER_TYPE", "chrome");
        }
        logger.info("Execution started with Browser:" + System.getProperty("BROWSER_TYPE").toUpperCase());
        switch (System.getProperty("BROWSER_TYPE").toUpperCase()) {
            case "CHROME":
                return new ChromeBrowser().newBrowser();
            case "FIREFOX":
                return new FirefoxBrowser().newBrowser();
            case "IE":
                return new IEBrowser().newBrowser();
            default:
                new ChromeBrowser();
        }
        return null;
    }

    public static WebDriver getBrowser() {
        WebDriver browser = createBrowser();
        logger.info("Browser Created Successfully");
        try {
            browser.get(PropertiesReader.getProperty("BASE_URL"));
            int implicitTimeout = Integer.parseInt(PropertiesReader.getProperty("IMPLICIT_TIMEOUT"));
            browser.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
        } catch (Exception ee) {
            ee.printStackTrace();
            logger.error("Exception occurred while performing setup browser");
        }
        return browser;
    }
}
