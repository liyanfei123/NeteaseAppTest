package com.liyanfei.test;

import com.liyanfei.base.BaseActivity;
import com.liyanfei.pages.HomePage;
import com.liyanfei.pages.LoginPage;
import com.liyanfei.util.*;
import io.appium.java_client.android.AndroidElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.tools.JavaCompiler;

public class AndroidTest extends BaseActivity {
    public static Actions actions;

    @Test
    public void test() throws Exception{
        AndroidElement element = FindElement.findElementByType(driver, "id",
                "com.netease.yanxuan:id/search_input");
        Actions.input(element, "黑猪肉");
//        element.clear();
//        element.sendKeys("黑猪肉");
//        /使用adb命令调起本地的输入法 。RuntimeUtil.execForStr是使用的hutool这个工具类

//        Runtime runtime = Runtime.getRuntime();
//        Process proc = runtime.exec("adb shell ime set com.sohu.inputmethod.sogou/.SogouIME");
        //在选中一下文本框让光标在定位一次
//        Actions.click(element, null);
//        actions.click(element, null);
//        element.click();
//        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        Actions.pressEnter(getDriver(), element);
    }

//    @Test
    public void test2() throws ActionExpection {
        actions = new Actions(getDriver());
        AndroidElement element;
        element = FindElement.findElementByType(driver, "id",
                "io.selendroid.testapp:id/showToastButton");
        actions.click(element, null);
//        boolean flag = actions.toastVerfity(driver, "Hello selendroid toast!");
//        if (flag) {
//            System.out.println("123456");
//        }
    }

//    @Test(dataProvider = "logindata")
    public void testLogin(String username, String password) {
        logger.info("初始化主页面");
        HomePage homePage = new HomePage(getDriver());
        logger.info("单击个人，进入用户登陆页面");
        LoginPage loginPage = homePage.loginPage();
        logger.info("输入用户名和密码，实现登陆");
//        loginPage.login(username, password);
    }

    @DataProvider(name = "logindata")
    public static Object[][] getLoginData() {
        return new Object[][] {
                new Object[] {"15806284945", "710662952lyf"}
        };
    }

}
