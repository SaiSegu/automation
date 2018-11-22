package com.java.automation.BrowserFactory;

import com.java.automation.Readers.PropertiesReader;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

class ChromeBrowser {
    final static Logger logger = Logger.getLogger(ChromeBrowser.class);
    public WebDriver newBrowser() {

        if(PropertiesReader.getProperty("HUB_URL") == null) {
            PropertiesReader.init();
        }
        if (PropertiesReader.getProperty("HUB_URL") != null) {
            return createBrowserOnHub(PropertiesReader.getProperty("HUB_URL"));
        } else {
            return createBrowserOnLocal();
        }
    }

    private WebDriver createBrowserOnLocal() {
        WebDriver driver;
        logger.info("Browser Execution started on local ");
        ChromeDriverManager.getInstance().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-notifications");

        try {
            driver = new ChromeDriver(options);
        } catch (Exception ee) {
            driver = null;
            ee.printStackTrace();
            logger.error("Exception while opening browser on local");
        }
        return driver;
    }

    private WebDriver createBrowserOnHub(String hub_url) {
        logger.info("Browser Execution started on HUB: " + hub_url);
        DesiredCapabilities dr = null;
        dr = DesiredCapabilities.chrome();
        dr.setBrowserName("chrome");
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(hub_url), dr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error("Exception occurred while opening browser onHUB: " + hub_url);
        }
        return driver;
    }
}
