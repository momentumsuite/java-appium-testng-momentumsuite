package localTest;

import io.appium.java_client.ios.IOSDriver;
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

public class iosLocalTest {
    public IOSDriver driver;
    public WebDriverWait wait;
    public String localHostUrl, localIOSApp, localIOSDeviceName;
    By elOne = By.xpath("(//*[contains(@label , '2')])[1]");
    By elTwo = By.xpath("//XCUIElementTypeButton[@name='+']");
    By elThree = By.xpath("//XCUIElementTypeButton[@name='5']");
    By elFour = By.xpath("//XCUIElementTypeButton[@name='=']");

    @BeforeTest
    public void setUp() throws MalformedURLException {

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/src/test/resources/config/testSettings.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject localJson = (JSONObject) jsonObject.get("LOCAL");
            JSONObject mobileJson = (JSONObject) localJson.get("ios");
            localHostUrl = (String) localJson.get("host");
            localIOSApp = (String) mobileJson.get("app");
            localIOSDeviceName = (String) mobileJson.get("deviceName");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:platformName", "iOS");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:autoAcceptAlerts", true);
        capabilities.setCapability("appium:language", "en");
        capabilities.setCapability("appium:locale", "US");
        capabilities.setCapability("appium:app", localIOSApp);
        capabilities.setCapability("appium:deviceName", localIOSDeviceName);
        capabilities.setCapability("appium:fullReset", true);
        capabilities.setCapability("appium:noReset", false);
        driver = new IOSDriver(new URL(localHostUrl), capabilities);
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

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
