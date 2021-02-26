package com.liyanfei.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;

public class UserPage {
    public static Logger logger = Logger.getLogger(LoginPage.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public UserPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }
}
