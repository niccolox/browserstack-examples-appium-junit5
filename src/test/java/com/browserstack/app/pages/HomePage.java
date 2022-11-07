package com.browserstack.app.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
// import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage extends BasePage {
    @AndroidFindBy(accessibility = "menu")
    @iOSXCUITFindBy(accessibility = "menu")
    private MobileElement menuLink;
    
    // select by ViewGroup filter-btn
    @AndroidFindBy(xpath = "//*[@content-desc = 'filter-btn']")
    @iOSXCUITFindBy(accessibility = "filter-btn")
    private MobileElement menuFilterButton;

    // select by ViewGroup text Samsung    
    @AndroidFindBy(xpath = "//*[@text = 'Samsung']")
    @iOSXCUITFindBy(accessibility = "filter-btn")
    private MobileElement menuFilterSamsung;    

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

    // select filter-btn and Samsung
    // to allow add to cart in next step 
    public HomePage navigateToSort() {
        menuFilterButton.click();
        menuFilterSamsung.click();
        mobileHelper.swipeLeft(driver.findElement(MobileBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup")));
        return this;
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
