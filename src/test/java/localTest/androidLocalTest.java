package localTest;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class androidLocalTest {
    public AndroidDriver driver;
    public WebDriverWait wait;
    public String localHostUrl, localAndroidApp, localAndroidDeviceName;
    By usernameField = By.id("app.com.sandjs.bankaccountfakewallet:id/username_txt");
    By passwordField = By.id("app.com.sandjs.bankaccountfakewallet:id/password_txt");
    By loginButton = By.id("app.com.sandjs.bankaccountfakewallet:id/login_btn");
    By balanceText = By.id("app.com.sandjs.bankaccountfakewallet:id/balance_txt");

    @BeforeTest
    public void setUp() throws MalformedURLException {

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/src/test/resources/config/testSettings.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject localJson = (JSONObject) jsonObject.get("LOCAL");
            JSONObject mobileJson = (JSONObject) localJson.get("android");
            localHostUrl = (String) localJson.get("host");
            localAndroidApp = (String) mobileJson.get("app");
            localAndroidDeviceName = (String) mobileJson.get("deviceName");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:platformName", "Android");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
        capabilities.setCapability("appium:autoGrantPermissions", true);
        capabilities.setCapability("appium:language", "en");
        capabilities.setCapability("appium:locale", "en");
        capabilities.setCapability("appium:app", localAndroidApp);
        capabilities.setCapability("appium:deviceName", localAndroidDeviceName);
        capabilities.setCapability("appium:fullReset", true);
        capabilities.setCapability("appium:noReset", false);
        driver = new AndroidDriver(new URL(localHostUrl), capabilities);
    }

    @Test()
    public void successfullyLoginTest() throws Exception {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys("first@mobven.com");
        driver.hideKeyboard();
        driver.findElement(passwordField).sendKeys("Pass321*");
        driver.findElement(loginButton).click();
        Thread.sleep(1000);
        String getBalanceText = wait.until(ExpectedConditions.visibilityOfElementLocated(balanceText)).getText();
        Assert.assertEquals(getBalanceText, "1000000", "Balance is not matched !");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }


}
