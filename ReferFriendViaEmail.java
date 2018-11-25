package com.test.automation.TestCases.USCRAFT;

import com.java.automation.Generic_Functions.SocialSiteManager;
import com.test.automation.hooks.BaseHook;
import org.testng.annotations.Test;

import java.util.Random;

public class ReferFriendViaEmail extends BaseHook {

    @Test(description = "US Cellular Postpaid RAF_Sign-In Gmail")
    public void inviteViaEmail() {
        String email = "Automation_" + new Random().nextFloat() + "@mailinator.com";
        uiActions.click("Header Sign In");
        uiActions.sendKeys("Email", "testya25@gmail.com");
        uiActions.sendKeys("Password", "Check1");
        uiActions.click("Continue");
        waitUtils.waitUntilElementVisible("My Rewards");
        uiActions.clickAndWaitForElement("Refer", "Refer By Email");
        uiActions.sendKeys("First Name", "Pravalika Automation");
        uiActions.sendKeys("Email", email);
        uiActions.clickAndWaitForElement("Email Sent Invite", "Email Sent Confirmation");
        boolean emailExist = SocialSiteManager.isEmailReceivedInMailinator(uiActions, waitUtils, email, "U.S. Cellular", "You've been invited!", 60);
        verify.validateTrue(emailExist, "Validate Email is received");
    }
}
