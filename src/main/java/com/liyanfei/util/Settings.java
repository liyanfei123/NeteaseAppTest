package com.liyanfei.util;

import java.io.File;

public class Settings {
    /**
     * 乐视手机配置
     */
    public static class LeShi {
        public static final String deviceName = "7ee19b31";
        public static final String platformName = "Android";
        public static final String platformVersion = "6.0";
        public static final String unicodeKeyboard = "true";
        public static final String resetKeyboard = "true";
        public static final String noSign = "true";
        public static final String appPackage = "com.netease.yanxuan";
        public static final String appActivity = "module.mainpage.activity.MainPageActivity";
    }

    /**
     * 元素控制的相关信息
     */
    public static class elementControl {
        public static final int elementWaitTime = 10;
        public static final int elementInspectInterval = 200;  //  控件识别间隔时间
    }

    /**
     * 活动控制的相关信息
     */
    public static class activityControl {
        public static final int activityInspectCount = 3;
        public static final int activityInspectInterval = 200;
    }

    /**
     * 登陆数据文件相关信息
     */
    public static class loginData {
        public static String dir = System.getProperty("user.dir") + File.separator +"data";
        public static String file = "loginData.xlsx";
        public static String sheetName = "login";
    }

    /**
     * 测试用例文件相关信息
     */
    public static class testCaseFile {
        public static String dir = System.getProperty("user.dir") + File.separator +"data";
        public static String file = "AllTestCase.xlsx";
    }

    /**
     * 所有测试用例的调度Sheet中的每个元素的序号
     */
    public static class testCaseSchedule {
        public static final String sheetName = "Main";
        public static final int testCaseName = 0;
        public static final int testCaseDetail = 1;
        public static final int isRun = 2;
        public static final int result = 3;
    }

    /**
     * 单个测试用例Sheet中的每个元素的序号
     */
    public static class testCase {
        public static final int testCaseId = 0;
        public static final int testStepId = 1;
        public static final int testStepDetail = 2;
        public static final int objectName = 3;
        public static final int locatedType = 4;
        public static final int inspector = 5;
        public static final int actionStep = 6;
        public static final int data = 7;
        public static final int result = 8;
    }
}
