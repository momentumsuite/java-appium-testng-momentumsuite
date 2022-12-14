package chromeTest;

import io.appium.java_client.AppiumDriver;
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

public class androidChromeTest {

    public AppiumDriver driver;
    public WebDriverWait wait;
    public String momentumUser, momentumToken, momentumHost;
    public Long momentumAndroidDeviceId;
    By elSearchButton = By.id("searchIcon");
    By elSearchText = By.xpath("//input[@name='search']");
    By assertText = By.id("firstHeading");


    @BeforeTest
    public void setUp() throws MalformedURLException {
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
            momentumAndroidDeviceId = (Long) deviceList.get(0);
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
        capabilities.setCapability("appium:browserName", "Chrome");
        capabilities.setCapability("appium:autoGrantPermissions", true);
        capabilities.setCapability("appium:language", "en");
        capabilities.setCapability("appium:locale", "en");
        capabilities.setCapability("appium:deviceName", "");
        capabilities.setCapability("appium:udid", "");
        driver = new AppiumDriver(new URL(momentumHost), capabilities);
    }

    @Test()
    public void chromeMobileBrowserTest() throws Exception {
        driver.get("https://en.wikipedia.org/");
        Thread.sleep(3000);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(elSearchButton)).click();
        Thread.sleep(2000);
        driver.findElement(elSearchText).sendKeys("momentum");
        Thread.sleep(2000);
        String assertTextString = wait.until(ExpectedConditions.visibilityOfElementLocated(assertText)).getText();
        Assert.assertEquals(assertTextString, "Momentum");
        Thread.sleep(2000);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
