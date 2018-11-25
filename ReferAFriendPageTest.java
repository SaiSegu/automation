package com.test.automation.TestCases.USCRAFT;

import com.test.automation.hooks.BaseHook;
import org.testng.annotations.Test;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


public class ReferAFriendPageTest extends BaseHook {

    @Test(description = "Verify Refer-A-Friend page displayed")
    public void validateReferAFriendPage() {
        uiActions.click("Header Sign In");
        uiActions.sendKeys("Email", "testya25@gmail.com");
        uiActions.sendKeys("Password", "Check1");
        uiActions.click("Continue");
        waitUtils.waitUntilElementVisible("My Rewards");
        uiActions.clickAndWaitForElement("Refer", "Refer By Email");

        uiActions.click("Refer By Email");
        verify.elementExists("Email", "On Refer via Email section Email exists");
        verify.elementExists("First Name", "On Refer via Email section First Name exists");

        uiActions.click("Refer By Facebook");
        verify.elementExists("Send Refer Message From Facebook", "Send Post Exist on Refer via facebook option");
        verify.elementExists("Facebook Post Sample Image", "Post is visible on Refer via Facebook");

        uiActions.click("Refer By Gmail");
        verify.elementExists("Import From Gmail", "Import email should get display on refer via Gmail");
        verify.elementExists("Send Refer Message From Gmail", "Send invite button should be visible on Gmail tab");


        uiActions.click("Refer By Twitter");
        verify.elementExists("Send Refer Message From Twitter", "Send Message is displayed");


        uiActions.click("Refer By Link");
        verify.elementExists("Copy Link", "Copy link button is exist");
        uiActions.click("Copy Link");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        try {
            String result = (String) clipboard.getData(DataFlavor.stringFlavor);
            verify.textOfElement("Refer Link", result, "After click on Copy link, Link should get copied on clipboard");
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
