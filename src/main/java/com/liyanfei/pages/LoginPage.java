package com.liyanfei.pages;

import com.liyanfei.util.ActionExpection;
import com.liyanfei.util.Actions;
import com.liyanfei.util.FindElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.HashMap;

public class LoginPage {
    public static Logger logger = Logger.getLogger(LoginPage.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public LoginPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    public void changeLoginWay(AndroidDriver<AndroidElement> driver) throws ActionExpection {
        // 切换登陆方式
        AndroidElement changeElement = FindElement.findElementByType(driver, "Id",
                "com.netease.yanxuan:id/btn_change_mode");
        Actions.click(changeElement, null);
    }

    public void inputInfo(String username, String password) throws ActionExpection {
        // 输入手机号
        AndroidElement usernameElement = FindElement.findElementByType(driver, "Id",
                "com.netease.yanxuan:id/account_edit");
        Actions.input(usernameElement, username);
        // 输入密码
        AndroidElement passwordElement = FindElement.findElementByType(driver, "Id",
                "com.netease.yanxuan:id/password_edit");
        Actions.input(passwordElement, password);
        // 勾选 同意 条款
        AndroidElement boxElement = FindElement.findElementByType(driver, "Id",
                "com.netease.yanxuan:id/check_box");
        Actions.click(boxElement, null);
    }

    public UserPage clickLogin() throws ActionExpection {
        // 点击登陆
        AndroidElement loginButton = FindElement.findElementByType(driver, "Id",
                "com.netease.yanxuan:id/btn_login_content");
        Actions.click(loginButton, null);
        return new UserPage(driver);
    }

    /**
     * 验证失败的登陆是否如期反馈信息
     *
     * @param driver
     * @param expected 期望的反馈信息
     * @return boolean
     */
    public boolean verfiyFail(AndroidDriver<AndroidElement> driver, String expected) {
        try {
            Actions.toastVerfity(null, expected);
            return true;
        } catch (ActionExpection e) {
            return false;
        }
    }
}
