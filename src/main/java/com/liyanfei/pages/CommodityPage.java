package com.liyanfei.pages;

import com.liyanfei.util.FindElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;
import java.util.List;

public class CommodityPage {
    public static Logger logger = Logger.getLogger(CommodityPage.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public CommodityPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    /**
     * 商品搜索结果页: 搜索第一件与我们输入的文本相匹配的商品
     * @param commodityName 待匹配的商品名
     * @return 第一个匹配的元素控件
     */
    public AndroidElement searchFirstMatchCommodity(String commodityName) {
        // 查找当前页面中的所有商品
        List<AndroidElement> elements = FindElement.findElementsByType(driver,
                "id", "com.netease.yanxuan:id/tv_goods_name");
        // 使用for循环遍历list中的每个元素
        for (AndroidElement element: elements) {
            if (element.getAttribute("text").contains(commodityName)) {
                return element;
            }
        }
        return null;
    }

    /**
     * 商品搜索结果页: 选择第一件与我们输入的文本相匹配的商品
     * @param element 待点击的元素
     * @return
     */
    public CommodityDetailPage selectFirstMatchCommodity(AndroidElement element) {
        element.click();
        return new CommodityDetailPage(driver);
    }
}
