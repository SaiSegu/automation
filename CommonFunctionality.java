package com.test.automation.hooks;

import com.java.automation.WebElementUtils.UIActions;
import com.java.automation.WebElementUtils.WaitUtils;

public class CommonFunctionality {

    public static void loginToApp(UIActions uiActions, WaitUtils waitUtils, String userName, String password) {
        userName = userName == null ? "testya25@gmail.com" : userName;
        password = password == null ? "Check1" : password;
        uiActions.click("Header Sign In");
        uiActions.sendKeys("Email", userName);
        uiActions.sendKeys("Password", password);
        uiActions.clickAndWaitForElement("Continue", "My Rewards");
    }

    public static String getRandomPhoneNumber() {
    return  ("" + ((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L)).replace("1", "9");
    }
}
