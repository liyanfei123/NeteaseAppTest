package com.liyanfei.test;

import com.liyanfei.base.BaseActivity;
import com.liyanfei.pages.HomePage;
import com.liyanfei.pages.LoginPage;
import com.liyanfei.util.*;
import io.appium.java_client.android.AndroidElement;
import org.aspectj.weaver.ast.And;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.tools.JavaCompiler;

public class AndroidTest extends BaseActivity {
    public static Actions actions;

//    @Test
    public void test2() throws ActionExpection {
        actions = new Actions(getDriver());
        AndroidElement element;
        element = FindElement.findElementByType(driver, "id",
                "io.selendroid.testapp:id/showToastButton");
        actions.click(element);
        boolean flag = actions.toastVerfity(driver, "Hello selendroid toast!");
        if (flag) {
            System.out.println("123456");
        }
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
