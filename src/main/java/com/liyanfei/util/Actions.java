package com.liyanfei.util;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.Logger;
import org.checkerframework.checker.units.qual.A;

import javax.swing.*;
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
    public static void click(AndroidElement element) throws ActionExpection {
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
                    click(element);
                }
            } else if (data.toLowerCase().equals("no")) {
                if (element.isSelected()) {
                    click(element);
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
            click(element);
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
     * 根据给定的文本验证当前元素是否为所需要的元素
     * @param element 待判断元素
     * @param data 判断文本
     * @return boolean
     */
    public static boolean verfiy(AndroidElement element, String data) {
        try {
            String actual = element.getAttribute("text");
            if (!actual.equals(data)) {
                logger.info("验证数据失败");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Toast消息验证
     * @param driver 驱动
     * @param data 验证文本
     * @return
     */
    public static boolean toastVerfity(AndroidDriver<AndroidElement> driver, String data) {
        try {
            String value = "//*[@text='" + data + "']";
            AndroidElement element = FindElement.findElementByType(driver, "xpath",
                    value);
            if (element != null && element.getAttribute("text").equals(data)) {
                return true;
            }
        } catch (Exception e) {}
        return false;
    }

    /**
     * 判断app跳转是否成功，通过activity来判断
     * @param data 期望的activity
     * @return
     * @throws InterruptedException
     */
    public static boolean loadingActivity(String data) throws InterruptedException {
        logger.info("当前活动页面activity为：" + driver.currentActivity());
        int count = Settings.activityControl.activityInspectCount;
        int interval = Settings.activityControl.activityInspectInterval;
        int i = 0;
        Thread.sleep(interval);
        while (i < count) {
            try {
                if (data.contains(driver.currentActivity())) {
                    logger.info(data + "出现");
                    return true;
                } else {
                    logger.info(data + "暂未出现！Waiting...");
                    Thread.sleep(interval);
                    i++;
                }
            } catch (Exception e) {
                i++;
            }
        }
        return false;
    }

    /**
     * 模拟按下确认搜索等
     * @param driver 驱动
     * @throws Exception
     */
    public static void pressEnter(AndroidDriver<AndroidElement> driver) throws Exception {
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

}
