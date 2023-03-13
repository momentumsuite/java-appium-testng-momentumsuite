package parallelTest;

import io.appium.java_client.android.AndroidDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class androidParallelTest {
    public AndroidDriver driver;
    public WebDriverWait wait;
    public String momentumUser, momentumToken, momentumHost, momentumAndroidApp;
    public Long momentumAndroidDeviceId;
    By usernameField = By.id("app.com.sandjs.bankaccountfakewallet:id/username_txt");
    By passwordField = By.id("app.com.sandjs.bankaccountfakewallet:id/password_txt");
    By loginButton = By.id("app.com.sandjs.bankaccountfakewallet:id/login_btn");
    By balanceText = By.id("app.com.sandjs.bankaccountfakewallet:id/balance_txt");

    @BeforeTest(alwaysRun=true)
    @org.testng.annotations.Parameters(value={"deviceIndex"})
    public void setUp(Integer deviceIndex) throws MalformedURLException {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/src/test/resources/config/testSettings.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject cloudJson = (JSONObject) jsonObject.get("CLOUD");
            JSONObject mobileJson = (JSONObject) cloudJson.get("android");
            JSONArray deviceList = (JSONArray) mobileJson.get("momentum.deviceList");
            momentumUser = (String) cloudJson.get("momentum.user");
            momentumToken = (String) cloudJson.get("momentum.token");
            momentumHost = (String) cloudJson.get("momentum.host");
            momentumAndroidApp = (String) mobileJson.get("momentum.app");
            momentumAndroidDeviceId = (Long) deviceList.get(deviceIndex);
            System.out.println("DEVICE is : " + momentumAndroidDeviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> momentumOptions = new HashMap<String, Object>();
        momentumOptions.put("user", momentumUser);
        momentumOptions.put("token", momentumToken);
        momentumOptions.put("gw", momentumAndroidDeviceId);
        capabilities.setCapability("momentum:options", momentumOptions);
        capabilities.setCapability("appium:platformName", "Android");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
        capabilities.setCapability("appium:autoGrantPermissions", true);
        capabilities.setCapability("appium:language", "en");
        capabilities.setCapability("appium:locale", "en");
        capabilities.setCapability("appium:deviceName", "");
        capabilities.setCapability("appium:udid", "");
        capabilities.setCapability("appium:app", momentumAndroidApp);
        capabilities.setCapability("appium:fullReset", true);
        capabilities.setCapability("appium:noReset", false);
        driver = new AndroidDriver(new URL(momentumHost), capabilities);
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

    @AfterTest(alwaysRun=true)
    public void tearDown() {
        driver.quit();
    }
}
