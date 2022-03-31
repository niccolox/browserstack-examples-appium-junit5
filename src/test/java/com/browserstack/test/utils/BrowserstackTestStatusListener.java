package com.browserstack.test.utils;

import com.browserstack.test.suites.TestBase;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BrowserstackTestStatusListener implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        if(TestBase.isRemote) {
            AppiumDriver<?> driver = ((TestBase)extensionContext.getTestInstance().get()).driver;
            if (extensionContext.getExecutionException().isPresent()) {
                String message = extensionContext.getExecutionException().toString();
                String reason = message != null && message.length() > 254 ? message.substring(0, 254) : message;
                markTestStatus("failed", reason.replaceAll("[^a-zA-Z0-9._-]", " "), driver);
            } else {
                markTestStatus("passed", "", driver);
            }
        }
    }

    private void markTestStatus(String status, String reason, WebDriver driver) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \"" + reason + "\"}}");
        } catch (Exception ex) {
            System.out.print("Error executing javascript" + ex);
        }
    }
}
