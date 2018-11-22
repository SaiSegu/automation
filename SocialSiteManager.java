package com.java.automation.Generic_Functions;

import com.java.automation.Readers.PropertiesReader;
import com.java.automation.WebElementUtils.UIActions;
import com.java.automation.WebElementUtils.WaitUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class SocialSiteManager {
    final static Logger logger = Logger.getLogger(UIActions.class);

    public static boolean isEmailReceivedInMailinator(UIActions uiActions, WaitUtils waitUtils, String to, String from, String subject, int timeout) {

        String mailinatorUri = PropertiesReader.getProperty("MAILINATOR_EMAIL_URL");
        String getEmailEndPoint = "v2/inbox.jsp?zone=public&query=" + to.replace("@mailinator.com", "");
        String emailURL = mailinatorUri + getEmailEndPoint;
        String emailRow = "(.//*[div[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='" + from.toLowerCase() + "'] and div[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')=\"" + subject.toLowerCase() + "\"]]//div[@title=\"TO\"])[1]";
        logger.info("Reading email from mailinator Details below");
        logger.info("emailURL:" + emailURL);
        uiActions.executeScript("window.open(\"" + emailURL + "\");");
        uiActions.switchToWindow("Mailinator");
        ObjectRepoHandler.addObject("emailField", By.xpath(emailRow));
        ObjectRepoHandler.addObject("Mailinator Container", By.id("inboxpane"));
        uiActions.navigateToFullURL(emailURL, "Mailinator Container");
        int count = 0;
        while (!uiActions.isElementDisplayed("emailField") && count < timeout) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            count++;
        }
        if (uiActions.isElementDisplayed("emailField")) {
            return true;
        } else {
            return false;
        }
    }

    //
    public static void loginToFacebook(UIActions uiActions, WaitUtils waitUtils, String userName, String password) {
        uiActions.sendKeys("Facebook Email", userName);
        uiActions.sendKeys("Facebook Password", password);
        uiActions.clickAndWaitForElement("Facebook Sign In", "Post to Facebook");
    }


    public static void loginToGmail(UIActions uiActions, WaitUtils waitUtils, String userName, String password) {
        uiActions.sendKeys("Gmail Email", userName);
        uiActions.clickAndWaitForElement("Gmail Email Next", "Gmail Password");
        uiActions.sendKeys("Gmail Password", password);
        uiActions.click("Gmail Password Next");
    }
}
