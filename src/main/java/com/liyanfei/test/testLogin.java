package com.liyanfei.test;

import com.liyanfei.base.BaseActivity;
import com.liyanfei.pages.HomePage;
import com.liyanfei.pages.LoginPage;
import com.liyanfei.util.ActionExpection;
import com.liyanfei.util.Actions;
import com.liyanfei.util.DataFromExcel;
import com.liyanfei.util.Settings;
import org.checkerframework.checker.units.qual.A;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 验证登陆账号格式等
 */
public class testLogin extends BaseActivity {
    public static Actions actions;

    @Test(dataProvider = "loginData")
    public void loginTest(HashMap<String, String> data) throws Exception {
        logger.info("初始化主页面");
        HomePage homePage = new HomePage(getDriver());
        logger.info("单击个人，进入用户登陆页面");
        LoginPage loginPage = homePage.loginPage();
        logger.info("切换为 使用密码登陆");
        loginPage.changeLoginWay(getDriver());
        logger.info("输入手机:" + data.get("telephone") + ", 和密码:" + data.get("password"));
        loginPage.inputInfo(data.get("telephone"), data.get("password"));
        // 由于Toast框显示有时间限制，故尝试三次判断
        boolean flag = false;
        for (int i = 0; i < 3; i++) {
            try {
                loginPage.clickLogin();
                flag = loginPage.verfiyFail(getDriver(), data.get("expected"));
                if (flag) {
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        Assert.assertTrue(flag);
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws Exception {
        ArrayList<HashMap<String, String>> datas = DataFromExcel.getAllDataByMaps(Settings.loginData.dir,
                Settings.loginData.file, Settings.loginData.sheetName);
        Object[][] datasNew = new Object[datas.size()][1];
        for (int i = 0; i < datas.size(); i++) {
            datasNew[i][0] = datas.get(i);
        }
        return datasNew;
    }
}
