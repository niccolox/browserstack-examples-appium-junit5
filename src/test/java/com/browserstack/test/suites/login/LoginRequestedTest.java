package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import io.appium.java_client.MobileBy;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("LoginRequestedTest")
public class LoginRequestedTest extends TestBase {

    @Test
    public void navigateFavoritesLoginRequested() {
        By favouritesMenuItem = mobileHelper.isAndroid() ? MobileBy.xpath("//*[@text = 'Favourites']") : MobileBy.id("Favourites");

        driver.findElement(MobileBy.AccessibilityId("menu")).click();
        driver.findElement(favouritesMenuItem).click();
        assertTrue(driver.findElement(MobileBy.AccessibilityId("login-form")).isDisplayed());
    }
}
