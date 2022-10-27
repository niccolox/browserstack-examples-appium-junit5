# Onboarding

  Maven:
  ```sh
mvn clean test -P bstack-single
  ```

  Maven:
  ```sh
mvn clean test -P bstack-parallel
  ```

  ```sh
mvn clean test -P bstack-single -Dsingle.test=LoginDataDrivenTest
  ```

# Single Onboarding

~/apache-maven-3.6.3/bin/mvn clean test -P bstack-single  

### Create a App URL

```
curl -u "BROWSERSTACK_USERNAME:BROWSERSTACK_ACCESS_KEY" \
-X POST "https://api-cloud.browserstack.com/app-automate/upload" \
-F "file=@/Users/nicholas/Projects/a-sample-apps/browserstack-demoapp.apk" \
-F "custom_id=BrowserStackDemoApp"
```

```
{"app_url":"bs://a6cf915943e8e2916475fb497e9457b62d7498f8","custom_id":"BrowserStackDemoApp","shareable_id":"BROWSERSTACK_USERNAME/BrowserStackDemoApp"}%   
```


### iPhone


```
curl -u "BROWSERSTACK_USERNAME:BROWSERSTACK_ACCESS_KEY" \
-X POST "https://api-cloud.browserstack.com/app-automate/upload" \
-F "file=@/Users/nicholas/Projects/a-sample-apps/browserstack-demoapp.ipa" \
-F "custom_id=BrowserStackDemoApp"
```

```
{"app_url":"bs://4aedeeb256630e1b8bd76b21c919da05312f99cf","custom_id":"BrowserStackDemoApp","shareable_id":"BROWSERSTACK_USERNAME/BrowserStackDemoApp"}% 
```

NOTE: better practice would be;
```BrowserStackDemoAppIos```


# Single Bstack

~/apache-maven-3.6.3/bin/mvn clean test -P bstack-single

### Appium Inpsector

 ![inspector](/docs/inspector.gif)

```
{
  "appium:app": "bs://a6cf915943e8e2916475fb497e9457b62d7498f8",
  "appium:deviceName": "Google Pixel 3",
  "platformName": "Android",
  "appium:platformVersion": "9.0"
}
```

https://www.browserstack.com/docs/app-automate/appium/integrations/appium-desktop


## Filter and Swipe

added ```navigateToSort()``` method to OrderTest.java Page Object.

```java
    @Test
    public void placeOrder() {
        HomePage page = new HomePage(driver)
                .navigateToSort()
                .addProductToCart("12")
                .navigateToSignIn()
                .loginWith("fav_user", "testingisfun99")
                .openCart()
                .proceedToCheckout()
                .enterShippingDetails("firstname", "lastname", "address", "state", "12345")
                .continueShopping();

        OrdersPage ordersPage = page.navigateToOrders();
        assertEquals(ordersPage.getItemsFromOrder(), 1);
    }
```

added 

```java
public class HomePage extends BasePage {
...    
    // select by ViewGroup filter-btn
    @AndroidFindBy(xpath = "//*[@content-desc = 'filter-btn']")
    @iOSXCUITFindBy(accessibility = "filter-btn")
    private MobileElement menuFilterButton;
    ...

    public HomePage navigateToSort() {
        menuFilterButton.click();
        menuFilterSamsung.click();
        mobileHelper.swipeLeft(driver.findElement(MobileBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup")));
        return this;
    }


```

### Example App Automate

https://app-automate.browserstack.com/dashboard/v2/builds/5fd898b9f761215bf396e06414915baccff67e2a/sessions/ef570a67a2efd4907000d9c21aae76873ec3a552?buildUserIds=6504358

### CI/CD tasks 

#### Launch test from Jenkins
![Jenkins Pipelie](/docs/jenkins-pipeline.gif)
  
#### Embedded reports
![Jenkins Pipelie](/docs/jenkins-report.gif)

#### Embedded reports
![Jenkins Pipelie](/docs/browserstack-plugin-creds.gif)

## OPEN ISSUES

1. test_caps.json only recognizes first device. Won't start test with multiple devices. I assume the TestBase.java has an limitation that is not seen in the similar implementation in Selenium BCDemo from which its modelled. Started to do a side by side compare, but have too many other onboarding tasks. 
2. parallel fails when run unchanged, is this by design? 

## TODO 
1. app upload not done