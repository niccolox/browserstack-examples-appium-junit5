package com.browserstack.test.suites.product;

import com.browserstack.test.suites.TestBase;
import io.appium.java_client.MobileBy;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("CartTest")
public class CartTest extends TestBase {

    @Test
    public void deleteProductFromCart() {
        By deleteButton = mobileHelper.isAndroid() ? MobileBy.xpath("//*[@text = 'Delete']") : MobileBy.id("Delete");
        
        mobileHelper.scrollToElement("add-to-cart-16");
        driver.findElement(MobileBy.AccessibilityId("add-to-cart-16")).click();

        driver.findElement(MobileBy.AccessibilityId("nav-cart")).click();
        
        mobileHelper.swipeLeft(driver.findElement(MobileBy.AccessibilityId("cart-item")));
        driver.findElement(deleteButton).click();
        
        assertEquals(driver.findElement(MobileBy.AccessibilityId("number-of-products")).getText(), "0 product(s) found.");
    }
}
