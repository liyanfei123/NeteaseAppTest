package com.liyanfei.pages;

import com.liyanfei.util.ActionExpection;
import com.liyanfei.util.Actions;
import com.liyanfei.util.FindElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class HomePage {
    public static Logger logger = Logger.getLogger(HomePage.class.getName());
    public static AndroidDriver<AndroidElement> driver;
    public static AndroidElement element;

    public HomePage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    /**
     * 主页: 在未登录的情况下，单击 个人，进入用户登陆界面
     * @return
     */
    public LoginPage loginPage() {
        logger.info("从主页进入用户登陆页");
        element = FindElement.findElementByType(driver,
                "xpath", "//android.widget.TabWidget/android.view.ViewGroup[5]");
        element.click();
        return new LoginPage(driver);
    }

    /**
     * 主页: 在搜索栏中输入待搜索待文本，并确认搜索
     * @param commodityName 待搜索的商品名
     * @return
     */
    public CommodityPage searchCommodity(String commodityName) {
        logger.info("搜索商品");
        element = FindElement.findElementByType(driver,
                "id", "com.netease.yanxuan:id/iv_home_search_icon");
        element.click();
        element = FindElement.findElementByType(driver,
                "id", "com.netease.yanxuan:id/search_input");
        element.clear();
        element.sendKeys(commodityName);
        try {
            // 模拟Android回车
            Actions.pressEnter(driver, element);
        } catch (ActionExpection e) {
            e.printStackTrace();
        }
        return new CommodityPage(driver);
    }
}
