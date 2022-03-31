package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import io.appium.java_client.MobileBy;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("LoginFailTest")
public class LoginFailTest extends TestBase {

    @Test
    public void loginFail() {
        driver.findElement(MobileBy.AccessibilityId("menu")).click();
        driver.findElement(MobileBy.AccessibilityId("nav-signin")).click();

        driver.findElement(MobileBy.AccessibilityId("username-input")).click();
        mobileHelper.selectFromPickerWheel("//XCUIElementTypePickerWheel[@value='Accepted usernames are']", "fav_user");

        driver.findElement(MobileBy.AccessibilityId("password-input")).click();
        mobileHelper.selectFromPickerWheel("//XCUIElementTypePickerWheel[@value='Password for all users']", "testingisfun99");

        driver.findElement(MobileBy.AccessibilityId("login-btn")).click();
        driver.findElement(MobileBy.AccessibilityId("menu")).click();
        assertEquals(driver.findElement(MobileBy.AccessibilityId("username")).getText(), "Welcome");
    }
}
