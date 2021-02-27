package com.liyanfei.util;

import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import java.util.function.Function;

public class FindElement {
    public static Logger logger = Logger.getLogger(FindElement.class.getName());

    /**
     * 根据给定的查找方式查找元素控件（仅查找一个时），同时使用显式等待
     * @param driver 驱动
     * @param locatedType 定位类型
     * @param locatedInfo 定位信息
     * @return 定位的一个元素
     * @throws NoSuchElementException 元素未找到
     */
    public static AndroidElement findElementByType(AndroidDriver<AndroidElement> driver,
               String locatedType, String locatedInfo) throws NoSuchElementException {
        AndroidElement element;
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
                case "cssselector":
                    element = WaitMostSeconds(driver, By.cssSelector(locatedInfo));
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
        throw new NoSuchElementException("控件获取失败");
    }

    /**
     * 根据给定的查找方式查找元素控件（查找多个时），同时使用显式等待
     * @param driver 驱动
     * @param locatedType 定位类型
     * @param locatedInfo 定位信息
     * @return 定位的多个元素
     * @throws NoSuchElementException 元素未找到
     */
    public static List<AndroidElement> findElementsByType(AndroidDriver<AndroidElement> driver,
                                                          String locatedType, String locatedInfo) throws NoSuchElementException {
        // 判断是否有该元素出现
        try {
            findElementByType(driver, locatedType, locatedInfo);
            List<AndroidElement> elements = new ArrayList<>();
            locatedType = locatedType.toLowerCase();
            switch (locatedType) {
                case "id":
                    elements = driver.findElementsById(locatedInfo);
                    break;
                case "classname":
                    elements = driver.findElementsByClassName(locatedInfo);
                    break;
                case "tagname":
                    elements = driver.findElementsByTagName(locatedInfo);
                    break;
                case "xpath":
                    elements = driver.findElementsByXPath(locatedInfo);
                    break;
                case "cssselector":
                    elements = driver.findElementsByCssSelector(locatedInfo);
                    break;
                case "androiduiautomator":
                    String value = "new UiSelector().text(" + "\"" + locatedInfo + "\"" + ")";
                    elements = driver.findElementsByAndroidUIAutomator(value);
                    break;
                case "accessibilityid":
                    elements = driver.findElementsByAccessibilityId(locatedInfo);
                    break;
                default:
                    throw new IllegalStateException("Unexpected type: " + locatedType);
            }
            return elements;
        } catch (NoSuchElementException e) {
            logger.info("控件未出现");
        } catch (Exception e) {
            logger.info("获取多个控件元素失败");
        }
        throw new NoSuchElementException("控件获取失败");
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
            return (AndroidElement) AppiumDriverWait.until(ExpectedConditions
                    .presenceOfElementLocated(by));
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException("元素控件未出现");
    }

    /**
     * 实现Android特有的元素定位方式的等待
     * @param driver 驱动
     * @return 等待对象
     */
    public static AppiumFluentWait<AndroidDriver> AndroidWait(AndroidDriver<?> driver) {
        return (AppiumFluentWait<AndroidDriver>) new AppiumFluentWait<AndroidDriver>(driver)
                .withTimeout(Duration.ofSeconds(Settings.elementControl.elementWaitTime))
                .pollingEvery(Duration.ofMillis(100));
    }
}
