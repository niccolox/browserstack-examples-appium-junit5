package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import io.appium.java_client.MobileBy;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("LoginDataDrivenReadFromCSVTest")
public class LoginDataDrivenReadFromCSVTest extends TestBase {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/users.csv")
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
