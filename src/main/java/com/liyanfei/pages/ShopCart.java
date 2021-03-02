package com.liyanfei.pages;

import com.liyanfei.util.FindElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.List;

public class ShopCart {

    public static Logger logger = Logger.getLogger(ShopCart.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public ShopCart(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    /**
     * 验证购物车中是否包含刚刚添加到商品
     * 后续可添加滑动的操作来验证，这边暂时默认在第一个
     * @param expected
     * @return
     */
    public boolean verfityCommodity(String expected) {
        List<AndroidElement> elements = FindElement.findElementsByType(driver,"id",
                "com.netease.yanxuan:id/commodity_info_name_tv");
//        List<AndroidElement> elements = driver.findElements(By.className("com.netease.yanxuan:id/commodity_info_name_tv"));
        // 使用for循环遍历list中的每个元素
        for (AndroidElement element: elements) {
//            System.out.println(element.getAttribute("text"));
            if (element.getAttribute("text").equals(expected)) {
               return true;
            }
        }
        return false;
    }
}
