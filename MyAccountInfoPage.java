package com.test.automation.TestCases.USCRAFT;

import com.test.automation.hooks.BaseHook;
import com.test.automation.hooks.CommonFunctionality;
import org.testng.annotations.Test;

import java.util.List;

public class MyAccountInfoPage extends BaseHook {


    @Test(description = "Validate UI of my info page")
    public void validateMyInfoPage() {
        CommonFunctionality.loginToApp(uiActions, waitUtils, null, null);
        uiActions.click("My Info");
        waitUtils.waitUntilElementsVisible("My Total Rewards");
        verify.textOfInputElement("First Name", "Test", "Validate value of First Name");
        verify.textOfInputElement("Last Name", "Ya", "Validate value of Last Name");
        verify.textOfInputElement("Address", "10 S", "Validate value of Address1");
        verify.textOfInputElement("City", "MSL", "Validate value of City");
        verify.selectedItemOfDropdown("State", "Minnesota", "Validate Value of selected State");
        verify.textOfInputElement("Zip Code", "55305", "Validate value of Zip Code");
        verify.textOfInputElement("Phone", "3078965458", "Validate value of Primary Phone number");
        verify.textOfElement("My Info Heading", "My Total Rewards: $0.00", "Verify Heading text of my info page");

        verify.textOfElement("My Info Paragraph",
                "Rewards may take up to 24 hours to display after all conditions are met",
                "Verify paragraph of my info page");
        verify.textOfElement("Your Membership Message 1",
                "If you are unsatisfied with the Refer-A-Friend Program, please contact Customer Service at 800-979-8447.",
                "Verify Info message on my info page");

        verify.textOfElement("Your Membership Message 2",
                "Would you like to reset your password? Click Here.",
                "Verify Info message on my info page");

        verify.textOfElement("Your Membership Message 3",
                "If you would like to cancel your membership Click Here.",
                "Verify Info message on my info page for Cancel membership");

    }

    @Test(description = "Edit functionality on My Info page", dependsOnMethods = "validateMyInfoPage")
    public void editOnMyInfoPage() {

        uiActions.click("Edit Profile");
        uiActions.clear("First Name");
        uiActions.clear("Last Name");
        uiActions.clear("Address");
        uiActions.clear("City");
        uiActions.clear("Zip Code");
        uiActions.click("Save Edit");

        List<String> myInfoPageErrorLabels = uiActions.getAllElementsText("My Info Page Error Messages");
        verify.equal(myInfoPageErrorLabels, testData.getList("My Info Page Invalid Error"), "Error message on My info page if entered only 1 char in field");
        uiActions.click("Cancel Edit");
        verify.textOfInputElement("First Name", "Test", "Validate value of First Name");
        verify.textOfInputElement("Last Name", "Ya", "Validate value of Last Name");
        verify.textOfInputElement("Address", "10 S", "Validate value of Address1");
        verify.textOfInputElement("City", "MSL", "Validate value of City");
        verify.selectedItemOfDropdown("State", "Minnesota", "Validate Value of selected State");
        verify.textOfInputElement("Zip Code", "55305", "Validate value of Zip Code");
        verify.textOfInputElement("Phone", "3078965458", "Validate value of Primary Phone number");
    }
}
