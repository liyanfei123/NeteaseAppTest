package com.liyanfei.pages;

import com.liyanfei.util.FindElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class CommodityDetailPage {
    public static Logger logger = Logger.getLogger(CommodityDetailPage.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public CommodityDetailPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }


    public void addShop() {
        AndroidElement element = FindElement.findElementByType(driver, "id",
                "com.netease.yanxuan:id/btn_buy_commodity_now");
        element.click();
    }

    /**
     * 以默认的方式添加到购物车中
     */
    public void addDefaultCommodity() {
        AndroidElement element = FindElement.findElementByType(driver, "id",
                "com.netease.yanxuan:id/btn_buy_commodity_now");
        element.click();
    }


    /**
     * 点击购物车小图标
     */
    public ShopCart clickShopCart() {
        AndroidElement element = FindElement.findElementByType(driver, "id",
                "com.netease.yanxuan:id/cart_iv_layout");
        element.click();
        return new ShopCart(driver);
    }
}
