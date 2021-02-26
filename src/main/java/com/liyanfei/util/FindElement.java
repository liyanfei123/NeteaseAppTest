package com.liyanfei.util;

import com.liyanfei.base.BaseActivity;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class FindElement {
    public static Logger logger = Logger.getLogger(FindElement.class.getName());

    /**
     * 根据给定的查找方式查找元素控件（仅查找一个时），同时使用显式等待
     * @param driver 驱动
     * @return 控件元素
     * @throws NoSuchElementException
     */
    public static AndroidElement findElementByType(AndroidDriver<AndroidElement> driver,
               String locatedType, String locatedInfo) throws NoSuchElementException {
        AndroidElement element = null;
        locatedType = locatedType.toLowerCase();
        try {
            switch (locatedType) {
                case "id":
                    element = WaitMostSeconds(driver, By.id(locatedInfo));
                    break;
                case "classname":
                    element = WaitMostSeconds(driver, By.className(locatedInfo));
                    break;
                case "tagname":
                    element = WaitMostSeconds(driver, By.tagName(locatedInfo));
                    break;
                case "xpath":
                    element = WaitMostSeconds(driver, By.xpath(locatedInfo));
                    break;
                    // Android特有的
                case "androiduiautomator":
                    String value = "new UiSelector().text(" + "\"" + locatedInfo + "\"" + ")";
                    element = AndroidWait(driver).until(new Function<AndroidDriver, AndroidElement>() {
                        @Override
                        public AndroidElement apply(AndroidDriver driver) {
                            return (AndroidElement) driver.findElementByAndroidUIAutomator(value);
                        }
                    });
                    break;
                case "accessibilityid":
                    value = locatedInfo;
                    element = AndroidWait(driver).until(new Function<AndroidDriver, AndroidElement>() {
                        @Override
                        public AndroidElement apply(AndroidDriver driver) {
                            return (AndroidElement) driver.findElementByAccessibilityId(value);
                        }
                    });
                    break;
                default:
                    throw new IllegalStateException("Unexpected type: " + locatedType);
            }
            return element;
        } catch (NoSuchElementException e) {
            logger.info("控件为出现");
        }
        throw new NoSuchElementException("控件未出现");
    }

    /**
     * Selenium方法等待元素出现
     * @param driver 驱动
     * @param by 元素定位方式
     * @return 元素控件
     */
    public static AndroidElement WaitMostSeconds(AndroidDriver<?> driver, By by) {
        WebDriverWait AppiumDriverWait = new WebDriverWait(driver, Settings.elementControl.elementWaitTime);
        try {
            AndroidElement element = (AndroidElement) AppiumDriverWait.until(ExpectedConditions
                    .presenceOfElementLocated(by));
            return element;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException("元素控件未出现");
    }

    /**
     * 为实现Android特有的元素定位方式的等待
     * @param driver 驱动
     * @return 等待对象
     */
    public static AppiumFluentWait<AndroidDriver> AndroidWait(AndroidDriver<?> driver) {
        return (AppiumFluentWait<AndroidDriver>) new AppiumFluentWait<AndroidDriver>(driver)
                .withTimeout(Duration.ofSeconds(Settings.elementControl.elementWaitTime))
                .pollingEvery(Duration.ofMillis(100));
    }


//
//        也可通过传入By对象获取元素，对应的方法为findElement(By.xx)和findElements(By.xx)
//
//        Android中还有一些专属方法，比如
//        findElementByAndroidUIAutomator()  // 通过UI Automator的定位方式来查找控件
//        findElementByAccessibilityId("xx") // 查找定位"content-desc"为“xx”的控件
//        如：AndroidElement menu = driver.findElementByAccessibilityId("显示根目录")
}
