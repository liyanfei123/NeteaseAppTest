package com.liyanfei.pages;

import com.liyanfei.util.FindElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class LoginPage {
    public static Logger logger = Logger.getLogger(LoginPage.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public LoginPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    public UserPage login(String username, String password) {
        // 切换登陆方式
        AndroidElement changeElement = FindElement.findElementByType(driver, "Id",
                "com.netease.yanxuan:id/btn_change_mode");
        changeElement.click();
        // 输入手机号
        AndroidElement usernameElement = FindElement.findElementByType(driver, "Id",
                "com.netease.yanxuan:id/account_edit");
        usernameElement.clear();
        usernameElement.sendKeys(username);
        // 输入密码
        AndroidElement passwordElement = FindElement.findElementByType(driver, "Id",
                "com.netease.yanxuan:id/password_edit");
        passwordElement.clear();
        passwordElement.sendKeys(password);
        // 勾选 同意 条款
        AndroidElement boxElement = FindElement.findElementByType(driver, "Id",
                "com.netease.yanxuan:id/check_box");
        boxElement.click();
        // 点击登陆
        AndroidElement loginButton = FindElement.findElementByType(driver, "Id",
                "e acom.netease.yanxuan:id/btn_login_content");
        loginButton.click();
        return new UserPage(driver);
    }
}
