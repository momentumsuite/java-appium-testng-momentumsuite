# Java Appium TestNG Momentumsuite


![image](https://user-images.githubusercontent.com/105457661/181139819-f73acae9-1a3b-4841-a536-983004d43cf1.png)

[TestNG](http://testng.org/) Integration with local or Momentum Suite real mobile farm devices

## Supports
  * Native or Hybrid Android and iOS apps (APK, AAB, IPA)
  * Chrome mobileweb testing on Android devices
  * Safari mobileweb testing on iOS devices
  * Parallel testing on multi mobile devices
  * Local testing or using Momentum Suite's 150+ Android or iOS devices
  * Auto generated HTML [Allure](https://docs.qameta.io/allure/) test report after test
  
  ## Setup

**Requirements:**

* Java 11+ If you don't have it installed, download it from [here](https://java.com/en/download/).
* Maven is required to run commands. Download it from [here](https://maven.apache.org/download.cgi).
* Install the [Allure command-line tool](https://www.npmjs.com/package/allure-commandline), and process the results directory after test run.

**Install the dependencies:**

Run the following command in project's base directory :
```
mvn clean
```

## Getting Started
Getting Started with Appium-Java tests on Momentum Suite couldn't be easier!
With a Momentum Suite account, You need 4 things to start without any Appium or Android SDK dependencies.
  * **momentum:user** Usually it could be your email address
  * **momentum:token** Your unique access token learned from momentumsuite.com
  * **momentum:gw** Comma seperated Momentum Suite mobile device ID list (4 digit number) to run the test. First number will be your default phone for all except parallel-testing.
  * **appium:app** Your uploaded IPA, APK or AAB app file from Momentum Suite Application Library. Example format is ms://<hashed-app-id> Optionally you can use a public accessible web URL.
 
 Do not forget to set these 4 Appium capability values and check hostname, port, path and protocol values on your **testSettings.json** file.

**Start with Android device:**
 Open for editing your testSettings.json file under [root directory](https://github.com/momentumsuite/java-appium-testng-momentumsuite/blob/main/src/test/resources/config/testSettings.json).
 
 Set momentum.user, momentum.token, momentum.deviceList, momentum.app on testSettings.json file.
 
 Test script is available in getting-started directory
 
 Run the following command in project's base directory :
```
 mvn clean test -P android-first
```


**Start with iOS device:**
Same with Android, but need to change testSettings.json file.
 
Run the following command in project's base directory :
```
 mvn clean test -P ios-first
```
 

**Start with local testing:**
Use Local testing that access resources hosted in your development or testing environments. You need to install Appium and it's all dependencies like Android SDK, Xcode, Command Line tools. At the same sime you will need to run a real device or simulator/emulator.  Do not forget to check hostname, port, path and protocol values on your test-settings.js file with your own Appium server.
 
Run the following command in project's base directory :
```
 mvn clean test -P android-local
```
 
 **All available commands to start mobile testing:**
 ```
 mvn clean test -P android-first
 mvn clean test -P android-local
 mvn clean test -P android-parallel
 mvn clean test -P android-chrome
 mvn clean test -P ios-first
 mvn clean test -P ios-local
 mvn clean test -P ios-parallel
 mvn clean test -P ios-safari
```

 
**Allure Reporting**
 
 Run the following command in project's base directory after test run has been completed. This command will open a browser window with HTML test results.
```
allure serve allure-results
```

## Getting Help
If you are running into any issues or have any queries, please check [Momentum Suite Contact page](https://www.momentumsuite.com/contact/) or get in touch with us.
 
Our Technical Documentation space is [here](https://www.momentumsuite.com/docs/).
