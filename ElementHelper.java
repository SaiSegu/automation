package com.java.automation.WebElementUtils;

import org.openqa.selenium.By;

public class ElementHelper {
    public static By formIdentifier(String identifier, String value) {
        switch (identifier.toUpperCase()) {
            case "XPATH":
                return By.xpath(value);
            case "CSS":
                return By.cssSelector(value);
            case "CLASS":
                return By.className(value);
            case "ID":
                return By.id(value);
            case "NAME":
                return By.name(value);
            case "LINK TEXT":
                return By.linkText(value);
            default:
                return By.cssSelector(value);
        }
    }

}
