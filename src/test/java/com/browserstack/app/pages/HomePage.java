package com.browserstack.app.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage extends BasePage {
    @AndroidFindBy(accessibility = "menu")
    @iOSXCUITFindBy(accessibility = "menu")
    private MobileElement menuLink;

    @AndroidFindBy(accessibility = "nav-signin")
    @iOSXCUITFindBy(accessibility = "nav-signin")
    private MobileElement signInLink;

    @AndroidFindBy(accessibility = "nav-cart")
    @iOSXCUITFindBy(accessibility = "nav-cart")
    private MobileElement cartLink;

    @AndroidFindBy(xpath = "//*[@text = 'Orders']")
    @iOSXCUITFindBy(id = "Orders")
    private MobileElement ordersLink;

    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    public LoginPage navigateToSignIn() {
        menuLink.click();
        signInLink.click();
        return new LoginPage(driver);
    }

    public HomePage addProductToCart(String productId) {
        mobileHelper.scrollToElement("add-to-cart-" + productId);
        driver.findElement(MobileBy.AccessibilityId("add-to-cart-" + productId)).click();
        return this;
    }

    public OrdersPage navigateToOrders() {
        menuLink.click();
        ordersLink.click();
        return new OrdersPage(driver);
    }

    public CartPage openCart() {
        cartLink.click();
        return new CartPage(driver);
    }
}
