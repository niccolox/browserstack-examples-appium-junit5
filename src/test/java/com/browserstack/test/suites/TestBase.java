package com.browserstack.test.suites;

import com.browserstack.local.Local;
import com.browserstack.test.utils.BrowserstackTestStatusListener;
import com.browserstack.test.utils.MobileHelper;
import com.browserstack.test.utils.ScreenshotListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ExtendWith({ScreenshotListener.class, BrowserstackTestStatusListener.class})
public class TestBase {
    private static final String PATH_TO_TEST_CAPS_JSON = "src/test/resources/conf/capabilities/test_caps.json";
    private static final String BROWSERSTACK_HUB_URL = "https://hub-cloud.browserstack.com/wd/hub";
    private static final long TIMESTAMP = (new Date()).getTime();
    private static final String appLocation = FileSystems.getDefault().getPath(System.getProperty("user.dir"), "/src/test/resources/app").toString();
    public AppiumDriver<?> driver;
    public MobileHelper mobileHelper;
    private static Local local;
    public static boolean isRemote = false;

    @BeforeEach
    public void setUp(TestInfo testInfo) throws Exception {
        String environment = System.getProperty("testEnv", "on-prem");
        String testType = System.getProperty("testType", "single");
        int env_cap_id = Integer.parseInt(System.getProperty("env_cap_id", "0"));

        JSONParser parser = new JSONParser();
        JSONObject testCapsConfig = (JSONObject) parser.parse(new FileReader(PATH_TO_TEST_CAPS_JSON));
        if (environment.equalsIgnoreCase("on-prem")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, testCapsConfig.get("platformName").toString());
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, testCapsConfig.get("deviceName").toString());
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, testCapsConfig.get("platformVersion").toString());
            capabilities.setCapability(MobileCapabilityType.APP, appLocation.concat(File.separator).concat(testCapsConfig.get("app.local").toString()));
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, testCapsConfig.get("automationName").toString());
            try {
                driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (environment.equalsIgnoreCase("remote")) {
            isRemote = true;
            JSONObject profilesJson = (JSONObject) testCapsConfig.get("tests");
            JSONObject envs = (JSONObject) profilesJson.get(testType);

            Map<String, String> envCapabilities = (Map) ((JSONArray) envs.get("env_caps")).get(env_cap_id);
            Map<String, String> localCapabilities = (Map<String, String>) envs.get("local_binding_caps");
            Map<String, String> commonCapabilities = (Map<String, String>) envs.get("common_caps");

            String app = envCapabilities.get(MobileCapabilityType.PLATFORM_NAME).equalsIgnoreCase("android") ? testCapsConfig.get("app.android").toString() : testCapsConfig.get("app.ios").toString();
            commonCapabilities.put(MobileCapabilityType.APP, app);
            commonCapabilities.put("name", testInfo.getTestMethod().get().getName());
            commonCapabilities.put("build", commonCapabilities.get("build") + " - " + TIMESTAMP);
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.merge(new DesiredCapabilities(commonCapabilities));
            caps.merge(new DesiredCapabilities(envCapabilities));
            if (testType.equals("local")) {
                caps.merge(new DesiredCapabilities(localCapabilities));
            }

            caps.setCapability("browserstack.user", getUsername(testCapsConfig));
            caps.setCapability("browserstack.key", getAccessKey(testCapsConfig));

            createSecureTunnelIfNeeded(caps, testCapsConfig);
            try {
                driver = new AppiumDriver(new URL(BROWSERSTACK_HUB_URL), caps);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (environment.equalsIgnoreCase("docker")) {
            System.out.println("TO BE DECIDED");
        }

        mobileHelper = new MobileHelper(driver);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    private String getUsername(JSONObject testCapsConfig) {
        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = testCapsConfig.get("user").toString();
        }
        return username;
    }  

    private String getAccessKey(JSONObject testCapsConfig) {
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = testCapsConfig.get("key").toString();
        }
        return accessKey;
    }

    private void createSecureTunnelIfNeeded(DesiredCapabilities caps, JSONObject testCapsConfig) throws Exception {
        if (caps.getCapability("browserstack.local") != null && caps.getCapability("browserstack.local").equals("true")) {
            local = new Local();
            UUID uuid = UUID.randomUUID();
            caps.setCapability("browserstack.localIdentifier", uuid.toString());
            Map<String, String> options = new HashMap<>();
            options.put("key", getAccessKey(testCapsConfig));
            options.put("localIdentifier", uuid.toString());
            local.start(options);
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        if (local != null) {
            local.stop();
        }
    }
}
