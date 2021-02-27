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

    public static class elementControl {
        public static final int elementWaitTime = 5;
        public static final int elementInspectCount = 3;   // 控件识别次数
        public static final int elementInspectInterval = 1000;  //  控件识别间隔时间
    }

    public static class activityControl {
        public static final int activityInspectCount = 3;
        public static final int activityInspectInterval = 1000;
    }

    public static class loginData {
        public static String dir = System.getProperty("user.dir") + File.separator +"data";
        public static String file = "loginData.xlsx";
        public static String sheetName = "login";
    }


}
