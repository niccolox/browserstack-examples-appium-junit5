package com.browserstack.app.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class CheckoutPage extends BasePage {
    @AndroidFindBy(accessibility = "firstNameInput")
    @iOSXCUITFindBy(accessibility = "firstNameInput")
    private MobileElement firstnameInput;

    @AndroidFindBy(accessibility = "lastNameInput")
    @iOSXCUITFindBy(accessibility = "lastNameInput")
    private MobileElement lastnameInput;

    @AndroidFindBy(accessibility = "addressInput")
    @iOSXCUITFindBy(accessibility = "addressInput")
    private MobileElement addressInput;

    @AndroidFindBy(accessibility = "stateInput")
    @iOSXCUITFindBy(accessibility = "stateInput")
    private MobileElement stateInput;

    @AndroidFindBy(accessibility = "postalCodeInput")
    @iOSXCUITFindBy(accessibility = "postalCodeInput")
    private MobileElement postcodeInput;

    @AndroidFindBy(accessibility = "submit-btn")
    @iOSXCUITFindBy(accessibility = "submit-btn")
    private MobileElement checkoutButton;

    public CheckoutPage(AppiumDriver driver) {
        super(driver);
    }

    public ConfirmationPage enterShippingDetails(String firstname, String lastname, String address, String state, String postcode) {
        firstnameInput.sendKeys(firstname);
        lastnameInput.sendKeys(lastname);
        addressInput.sendKeys(address);
        stateInput.sendKeys(state);
        postcodeInput.sendKeys(postcode);
        driver.hideKeyboard();
        checkoutButton.click();
        return new ConfirmationPage(driver);
    }
}
