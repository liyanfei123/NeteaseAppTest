package com.liyanfei.test;

import com.liyanfei.base.BaseActivity;
import com.liyanfei.pages.*;
import com.liyanfei.util.ActionExpection;
import com.liyanfei.util.Actions;
import io.appium.java_client.android.AndroidElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class shopTest extends BaseActivity {

    /**
     * 在未登陆的情况下进行购物车加购测试
     * @param commodityName 商品名称
     * @param telephone 登陆手机号码
     * @param password 登陆密码
     * @throws Exception
     */
    @Test(dataProvider = "shopData")
    public void addShop(String commodityName, String telephone, String password) throws Exception {
        // 未登陆的情况下
        logger.info("初始化主页面");
        HomePage homePage = new HomePage(getDriver());
        logger.info("输入搜索的商品，并确认搜索, 进入搜索商品结果页面");
        CommodityPage commodityPage = homePage.searchCommodity(commodityName);
        logger.info("选择与我们输入的文本匹配的第一个商品，进入商品详情页");  // 这边我们默认选择第一款商品进行测试
        AndroidElement element = commodityPage.searchFirstMatchCommodity(commodityName);
        String expectedText = element.getAttribute("text");
        System.out.println(expectedText);
        CommodityDetailPage commodityDetailPage = commodityPage.selectFirstMatchCommodity(element);
        logger.info("点击 加入购物车");
        commodityDetailPage.addShop();
        logger.info("直接加购，选择默认规格和数量，及支付方式");
        commodityDetailPage.addDefaultCommodity();
        logger.info("弹出登陆页面");
        logger.info("切换为 使用密码登陆");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.changeLoginWay();
        logger.info("输入手机:" + telephone + ", 和密码:" + password);
        loginPage.inputInfo(telephone, password);
        logger.info("点击登陆");
        loginPage.clickLogin();
        Thread.sleep(3000);
        logger.info("验证加入购物车是否成功"); // 有 加入购物车成功 Toast
        Actions actions = new Actions(getDriver());
        try {
            actions.toastVerfity(null, "加入购物车成功");
        } catch (ActionExpection e) {
            logger.info("Toast捕获失败");
        }
        logger.info("点击购物车");
        ShopCart shopCart = commodityDetailPage.clickShopCart();
        logger.info("验证是否存在加购商品"); // 通过关键字文本来进行判断
        Assert.assertTrue(shopCart.verfityCommodity(expectedText));
    }


    @DataProvider(name = "shopData")
    public Object[][] shopData() {
        return new Object[][] {
                {"黑猪肉香肠", "xx", "xx"}
        };
    }
}
