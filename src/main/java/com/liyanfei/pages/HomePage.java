package com.liyanfei.pages;

import com.liyanfei.util.FindElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class HomePage {
    public static Logger logger = Logger.getLogger(HomePage.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public HomePage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    /**
     * 单击个人，进入用户登陆界面
     * @return
     */
    public LoginPage loginPage() {
        logger.info("从主页进入用户登陆页");
        AndroidElement element = FindElement.findElementByType(driver, "Xpath",
                "//android.widget.TabWidget/android.view.ViewGroup[5]");
        element.click();
        return new LoginPage(driver);
    }
}