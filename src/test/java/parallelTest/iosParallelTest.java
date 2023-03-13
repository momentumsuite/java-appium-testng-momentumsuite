package parallelTest;

import io.appium.java_client.ios.IOSDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class iosParallelTest {
    public IOSDriver driver;
    public WebDriverWait wait;
    public String momentumUser, momentumToken, momentumHost, momentumIOSApp, remoteDebugProxy;
    public Long momentumIOSDeviceId;
    By elOne = By.xpath("(//*[contains(@label , '2') or contains(@name , '2') or contains(@value , '2')])[1]");
    By elTwo = By.xpath("//XCUIElementTypeButton[@name='+']");
    By elThree = By.xpath("//XCUIElementTypeButton[@name='5']");
    By elFour = By.xpath("//XCUIElementTypeButton[@name='=']");

    @BeforeTest(alwaysRun = true)
    @org.testng.annotations.Parameters(value = {"deviceIndex"})
    public void setUp(Integer deviceIndex) throws MalformedURLException {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/src/test/resources/config/testSettings.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject cloudJson = (JSONObject) jsonObject.get("CLOUD");
            JSONObject mobileJson = (JSONObject) cloudJson.get("ios");
            JSONArray deviceList = (JSONArray) mobileJson.get("momentum.deviceList");
            momentumUser = (String) cloudJson.get("momentum.user");
            momentumToken = (String) cloudJson.get("momentum.token");
            momentumHost = (String) cloudJson.get("momentum.host");
            momentumIOSApp = (String) mobileJson.get("momentum.app");
            momentumIOSDeviceId = (Long) deviceList.get(deviceIndex);
            remoteDebugProxy = String.valueOf(momentumIOSDeviceId + 2000);
            System.out.println("DEVICE is : " + momentumIOSDeviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> momentumOptions = new HashMap<String, Object>();
        momentumOptions.put("user", momentumUser);
        momentumOptions.put("token", momentumToken);
        momentumOptions.put("gw", momentumIOSDeviceId);
        capabilities.setCapability("momentum:options", momentumOptions);
        capabilities.setCapability("appium:platformName", "iOS");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:autoAcceptAlerts", true);
        capabilities.setCapability("appium:language", "en");
        capabilities.setCapability("appium:locale", "US"); 
        capabilities.setCapability("appium:deviceName", "");
        capabilities.setCapability("appium:udid", "");
        capabilities.setCapability("appium:app", momentumIOSApp);
        capabilities.setCapability("appium:fullReset", true);
        capabilities.setCapability("appium:noReset", false);
        capabilities.setCapability("appium:remoteDebugProxy", remoteDebugProxy);
        driver = new IOSDriver(new URL(momentumHost), capabilities);
    }

    @Test()
    public void calculateTwoNumbersTest() throws Exception {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(elOne)).click();
        Thread.sleep(1000);
        driver.findElement(elTwo).click();
        driver.findElement(elThree).click();
        driver.findElement(elFour).click();
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
