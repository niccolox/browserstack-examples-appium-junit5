package com.browserstack.app.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ConfirmationPage extends BasePage {
    @AndroidFindBy(accessibility = "continue-btn")
    @iOSXCUITFindBy(accessibility = "continue-btn")
    private MobileElement continueShoppingButton;

    public ConfirmationPage(AppiumDriver driver) {
        super(driver);
    }

    public HomePage continueShopping() {
        continueShoppingButton.click();
        return new HomePage(driver);
    }
}
