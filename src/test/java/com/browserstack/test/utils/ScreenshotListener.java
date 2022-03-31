package com.browserstack.test.utils;

import com.browserstack.test.suites.TestBase;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotListener implements AfterTestExecutionCallback {

    @Attachment(value = "Failure screenshot", type = "image/png")
    public byte[] attachFailedScreenshot(WebDriver driver) {
        return (byte[]) ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        AppiumDriver<?> driver = ((TestBase)extensionContext.getTestInstance().get()).driver;
        attachFailedScreenshot(driver);
    }
}
