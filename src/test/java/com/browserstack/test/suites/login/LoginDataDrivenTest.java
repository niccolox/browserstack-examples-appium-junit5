package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import io.appium.java_client.MobileBy;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("LoginDataDrivenTest")
public class LoginDataDrivenTest extends TestBase {

    @ParameterizedTest
    @CsvSource({
            "locked_user, testingisfun99, Your account has been locked.",
            "fav_user, wrong_password, Invalid Password",
            "invalid_user, testingisfun99, Invalid Username"
    })
    public void validateErrors(String username, String password, String error) {
        driver.findElement(MobileBy.AccessibilityId("menu")).click();
        driver.findElement(MobileBy.AccessibilityId("nav-signin")).click();

        driver.findElement(MobileBy.AccessibilityId("username-input")).click();
        mobileHelper.selectFromPickerWheel("//XCUIElementTypePickerWheel[@value='Accepted usernames are']", username);

        driver.findElement(MobileBy.AccessibilityId("password-input")).click();
        mobileHelper.selectFromPickerWheel("//XCUIElementTypePickerWheel[@value='Password for all users']", password);

        driver.findElement(MobileBy.AccessibilityId("login-btn")).click();
        assertEquals(driver.findElement(MobileBy.AccessibilityId("api-error")).getText(), error);
    }
}
