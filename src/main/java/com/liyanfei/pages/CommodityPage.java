package com.liyanfei.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;

public class CommodityPage {
    public static Logger logger = Logger.getLogger(CommodityPage.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public CommodityPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

}
