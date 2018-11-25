package com.test.automation.TestCases.USCRAFT;

import com.codoid.products.exception.FilloException;
import com.test.automation.hooks.BaseHook;
import org.testng.annotations.Test;

public class LandingPageTest extends BaseHook {
	
	
	@Test(description = "Validate Page Content")
	    public void ValidateHeaderTextAndParagraph() throws FilloException {
	        verify.textOfElementContains("Heading Text", testData.get("Heading Text"),
	                "Landing page Header should be '" + testData.get("Heading Text") + "'");
	        verify.textOfElementContains("Heading Paragraph", testData.get("Heading Paragraph"),
	                "Landing page Header Text should be '" + testData.get("Heading Paragraph") + "'");
	        verify.textOfElementContains("Text Above Sign Up", "Start winning today!",
	                "Landing page should contain '" + "Start winning today!" );
	    }

    @Test(description = "Click on Logo and verify navigation to Sign In Page")
    public void OnLogoClickLoginNavigation() {
        uiActions.click("Logo");
        verify.elementExists("Email", "Sign In page should be displayed when user click on logo");
        uiActions.click("Click here in Learn More");
    }

    @Test(description = "After Clicking on SignIn page, User should get navigated to login page")
    public void OnSignInButtonClickLoginNavigation() {
        uiActions.click("Header Sign In");
        verify.elementExists("Email", "Sign In page should be displayed when user click on Header Sign In button");
        uiActions.click("Click here in Learn More");
    }


    @Test(description = "Validate Footer")
    public void ValidateFooter() throws FilloException {
        String expectedFooterParagraph = testData.get("Footer paragraph");
        String expectedFooterTitle = testData.get("Footer Heading");
        verify.elementExists("Footer Heading", "Footer heading should be displayed");
        verify.textOfElement("Footer Heading", expectedFooterTitle,
                "Footer should have '" + expectedFooterTitle + "'" + " heading");
        verify.textOfElement("Footer Paragraph", expectedFooterParagraph,
                "Footer  should have '" + expectedFooterParagraph + " Paragraph");
        uiActions.click("Privacy Policy");
        verify.windowsCount(uiActions.openWindows(), 2, "new window is opened", "After click on 'Privacy Policy' Link, New window should get opened");
        uiActions.switchToWindow("Privacy Information Highlights | U.S. Cellular");
        verify.partialEqual("https://www.uscellular.com/site/privacy/index.html", uiActions.getCurrentPageURL(), "Click on Privacy Policy leads to 'https://www.uscellular.com/site/privacy/index.html'");
        uiActions.closeCurrentWindowAndReturnToParent();
        verify.windowsCount(uiActions.openWindows(), 1,"window can be closed", "User should be able to close 'Privacy Policy' window");
        uiActions.click("Terms of Use");
        verify.windowsCount(uiActions.openWindows(), 2,"new window is opened", "After click on 'Terms of Use' Link, New window should get opened");
        uiActions.switchToWindow("4225.pdf");
        uiActions.closeCurrentWindowAndReturnToParent();
        verify.windowsCount(uiActions.openWindows(), 1,"window can be closed", "User should be able to close 'Terms of Use' window");
    }

}
