package com.liyanfei.pages;

import com.liyanfei.util.FindElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;

public class CommodityDetailPage {
    public static Logger logger = Logger.getLogger(CommodityDetailPage.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public CommodityDetailPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }


    /**
     * 商品详情页: 选择商品的规格后，再点击加入购物车
     *
     */
    public void addShop() {
        // 此处可添加数量规格等信息
        addDefaultCommodity();
    }

    /**
     * 商品详情页: 以默认规格的方式添加到购物车中
     */
    public void addDefaultCommodity() {
        AndroidElement element = FindElement.findElementByType(driver,
                "id", "com.netease.yanxuan:id/btn_buy_commodity_now");
        element.click();
    }


    /**
     * 商品详情页: 点击购物车小图标，进入购物车页面
     */
    public ShopCart clickShopCart() {
        AndroidElement element = FindElement.findElementByType(driver,
                "id", "com.netease.yanxuan:id/cart_iv_layout");
        element.click();
        return new ShopCart(driver);
    }
}
