package com.liyanfei.util;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.Logger;

import java.time.Duration;

public class Actions {
    public static Logger logger = Logger.getLogger(Actions.class.getName());
    public static AndroidDriver<AndroidElement> driver;

    public Actions(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }


    /**
     * 实现元素的单击操作
     * @param element 待单击的元素
     */
    public static void click(AndroidElement element, String data) throws ActionExpection {
        try {
            element.click();
//            logger.info("单击成功！");
        } catch (Exception e) {
            // 单击操作出现异常，等待重试
            try {
                Thread.sleep(Settings.elementControl.elementInspectInterval);
            } catch (InterruptedException ex) {
                throw new ActionExpection("点击操作发生异常！");
            }
            element.click();
        }
    }

    /**
     * 根据传入的数据判断单选框是否需要选择
     * @param element 单选框
     * @param data 是否选中
     * @throws ActionExpection
     */
    public static void radioClick(AndroidElement element, String data) throws ActionExpection {
        try {
            if (data.toLowerCase().equals("yes")) {
                if (!element.isSelected()) {
                    click(element, null);
                }
            } else if (data.toLowerCase().equals("no")) {
                if (element.isSelected()) {
                    click(element, null);
                }
            }
        } catch (Exception e) {
            throw new ActionExpection("单选操作发生异常！");
        }
    }


    /**
     * 向指定的元素中输入文本
     * @param element 元素
     * @param data 写入文本
     * @throws ActionExpection
     */
    public static void input(AndroidElement element, String data) throws ActionExpection {
        try {
            click(element, null);
            element.clear();
            element.sendKeys(data);
        } catch (Exception e) {
            throw new ActionExpection("文本输入发生错误");
        }
    }


    /**
     * 实现滑动操作
     * @param startx 起点x坐标
     * @param starty 起点y坐标
     * @param endx 终点x坐标
     * @param endy 终点y坐标
     * @param duration 持续时间
     * @throws ActionExpection
     */
    public static void swipe(int startx, int starty, int endx, int endy, int duration)
        throws ActionExpection {
        try {
            TouchAction touchAction = new TouchAction(driver);
            touchAction.press(PointOption.point(startx, starty))   // 按压指定点
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))  // 滑动间隔时间
                    .moveTo(PointOption.point(endx, endy)).release();   // 移动到终点  释放
            touchAction.perform();  // 执行操作
        } catch (Exception e) {
            throw new ActionExpection("滑动操作发生错误");
        }
    }

    /**
     * 实现长按操作
     * @param element 待长按的点
     * @param data 长按的时间
     * @throws ActionExpection
     */
    public static void longPress(AndroidElement element, String data) throws ActionExpection {
        try {
            TouchAction touchAction = new TouchAction(driver);
            touchAction.longPress(PointOption.point(element.getLocation().getX(), element.getLocation().getY())).
                    waitAction(WaitOptions.waitOptions(Duration.ofSeconds(Integer.valueOf(data)))).release().perform();
        } catch (Exception e) {
            throw new ActionExpection("长按操作发生错误");
        }
    }


    /**
     * 暂停操作
     * @param element 无实际意义
     * @param data 暂停时间
     * @throws ActionExpection
     */
    public static void sleep(AndroidElement element, String data) throws ActionExpection {
        try {
            Thread.sleep(3 * 1000);
        }catch (Exception e) {
            throw new ActionExpection("等待出错！");
        }
    }

    /**
     * 根据给定的文本验证当前元素是否为所需要的元素
     * @param element 待判断元素
     * @param data 期望文本
     */
    public static void testVerfiy(AndroidElement element, String data) throws ActionExpection{
        try {
            String actual = element.getAttribute("text");
            if (!actual.equals(data)) {
                logger.info("验证数据失败");
                throw new ActionExpection("验证数据失败");
            }
//            return true;
        } catch (Exception e) {
            throw new ActionExpection("验证数据发生异常");
        }
    }


    /**
     * 验证控件元素是否被选中
     * @param element 待判断的元素
     * @param data 期望结果
     * @throws ActionExpection
     */
    public static void selectedVerfity(AndroidElement element, String data) throws ActionExpection{
        try {
            boolean flag = Boolean.valueOf(data).booleanValue();
            if (element.isSelected() != flag) {
                throw new ActionExpection("是否选中验证失败");
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw new ActionExpection("验证是否选中发生异常");
        }
    }

    /**
     * Toast消息验证
     * @param element 无实际意义
     * @param data 验证文本
     * @return
     */
    public static void toastVerfity(AndroidElement element, String data) throws ActionExpection {
        try {
            String value = "//*[@text='" + data + "']";
            element = FindElement.findElementByType(driver, "xpath",
                    value);
            if (element != null && element.getAttribute("text").equals(data)) {}
            else {
                throw new ActionExpection("验证错误");
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw new ActionExpection("验证发生异常");
        }
    }


    /**
     * 判断app跳转是否成功，通过activity来判断
     * @param data 驱动
     * @param data 期望的activity
     * @return
     * @throws InterruptedException
     */
    public static void loadingActivity(AndroidDriver<AndroidElement> driver, String data) throws ActionExpection, InterruptedException {
        logger.info("当前活动页面activity为：" + driver.currentActivity());
        int count = Settings.activityControl.activityInspectCount;
        int interval = Settings.activityControl.activityInspectInterval;
        int i = 0;
        Thread.sleep(interval);
        while (i < count) {
            try {
                if (data.contains(driver.currentActivity())) {
                    logger.info(data + "出现");
                    break;
                } else {
                    logger.info(data + "暂未出现！Waiting...");
                    Thread.sleep(interval);
                    i++;
                }
            } catch (Exception e) {
                i++;
            }
        }
        throw new ActionExpection("验证发生意外");
    }

    /**
     * 模拟Android的回车操作，如确认搜索
     * @param driver 驱动
     * @param element 待确认的元素
     * @throws ActionExpection
     */
    public static void pressEnter(AndroidDriver<AndroidElement> driver, AndroidElement element) throws ActionExpection {
        try {
            // 切换本地输入法，确保搜索可以成功
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("adb shell ime set com.sohu.inputmethod.sogou/.SogouIME");
            Actions.click(element, null);
            driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionExpection("按键发生异常！");
        }
    }

}
