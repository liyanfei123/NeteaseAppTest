package com.liyanfei.base;

import com.liyanfei.util.Settings;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.URL;

public class BaseActivity {
    public static Logger logger = Logger.getLogger(BaseActivity.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    @BeforeClass
    public void setUp() throws Exception {
        PropertyConfigurator.configure("config/log4j.properties");
        logger.info("----------测试用例开始执行----------");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("deviceName", Settings.LeShi.deviceName);
        desiredCapabilities.setCapability("platformName", Settings.LeShi.platformName);
        desiredCapabilities.setCapability("platfromVersion", Settings.LeShi.platformVersion);
        desiredCapabilities.setCapability("unicodeKeyboard", Settings.LeShi.unicodeKeyboard);
        desiredCapabilities.setCapability("resetKeyboard", Settings.LeShi.resetKeyboard);
        desiredCapabilities.setCapability("noSign", Settings.LeShi.noSign);
        // 确保可识别Toast
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
//        desiredCapabilities.setCapability("appPackage", Settings.LeShi.appPackage);
//        desiredCapabilities.setCapability("appActivity", Settings.LeShi.appActivity);
        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
        driver.hideKeyboard();
    }

    @BeforeMethod
    public void setUpMethod() {
        String androidPackage = Settings.LeShi.appPackage;
        String androidStartActivity = Settings.LeShi.appActivity;
        driver.startActivity(new Activity(androidPackage, androidStartActivity));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    public AndroidDriver<AndroidElement> getDriver() {
        return driver;
    }
}
