package com.liyanfei.base;

import com.liyanfei.util.Settings;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

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
//        desiredCapabilities.setCapability("appPackage", Settings.LeShi.appPackage);
//        desiredCapabilities.setCapability("appActivity", Settings.LeShi.appActivity);
        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    public AndroidDriver<AndroidElement> getDriver() {
        return driver;
    }
}
