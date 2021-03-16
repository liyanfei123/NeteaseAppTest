package com.liyanfei.pages;

import com.liyanfei.util.ActionExpection;
import com.liyanfei.util.Actions;
import com.liyanfei.util.FindElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;

public class LoginPage {
    public static Logger logger = Logger.getLogger(LoginPage.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public LoginPage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    /**
     * 登陆页: 切换登陆方式，切换换使用密码登陆
     * @throws ActionExpection
     */
    public void changeLoginWay() throws ActionExpection {
        AndroidElement changeElement = FindElement.findElementByType(driver, "id",
                "com.netease.yanxuan:id/btn_change_mode");
        Actions.click(changeElement, null);
    }

    /**
     * 登陆页: 输入手机号和密码
     * @param telephone 手机号
     * @param password 密码
     * @throws ActionExpection
     */
    public void inputInfo(String telephone, String password) throws ActionExpection {
        // 输入手机号
        AndroidElement telElement = FindElement.findElementByType(driver, "id",
                "com.netease.yanxuan:id/account_edit");
        Actions.input(telElement, telephone);
        // 输入密码
        AndroidElement passwordElement = FindElement.findElementByType(driver, "id",
                "com.netease.yanxuan:id/password_edit");
        Actions.input(passwordElement, password);
    }

    /**
     * 登陆页: 点击 登陆
     * @return
     * @throws ActionExpection
     */
    public UserPage clickLogin() throws ActionExpection {
        // 勾选 同意 条款
        AndroidElement boxElement = FindElement.findElementByType(driver, "id",
                "com.netease.yanxuan:id/check_box");
        Actions.click(boxElement, null);
        // 点击登陆
        AndroidElement loginButton = FindElement.findElementByType(driver, "id",
                "com.netease.yanxuan:id/btn_login_content");
        Actions.click(loginButton, null);
        return new UserPage(driver);
    }

    /**
     * 登陆页: 验证失败的登陆是否如期反馈信息
     * @param driver 驱动
     * @param expected 期望的反馈信息
     * @return boolean
     */
    public boolean verfiyFail(AndroidDriver<AndroidElement> driver, String expected) {
        try {
            Actions actions = new Actions(driver);
            actions.toastVerfity(null, expected);
            return true;
        } catch (ActionExpection e) {
            return false;
        }
    }
}
