package ru.kuz.helper;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.time.Duration;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ScrollHelper {

    private static AndroidDriver getDriver() {
        return (AndroidDriver) getWebDriver();
    }

    /**
     * Скролл вниз на указанный процент экрана
     * @param percent процент от высоты экрана (0.5 = половина, 0.8 = 80%)
     */
    public static void scrollDown(double percent) {
        int height = getDriver().manage().window().getSize().height;
        int width = getDriver().manage().window().getSize().width;

        int startX = width / 2;
        int startY = (int) (height * 0.7);  // начинаем с 70% высоты
        int endY = (int) (height * (0.7 - percent));  // скроллим вверх на percent

        new TouchAction<>(getDriver())
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    /**
     * Скролл вверх на указанный процент экрана
     */
    public static void scrollUp(double percent) {
        int height = getDriver().manage().window().getSize().height;
        int width = getDriver().manage().window().getSize().width;

        int startX = width / 2;
        int startY = (int) (height * 0.3);  // начинаем с 30% высоты
        int endY = (int) (height * (0.3 + percent));  // скроллим вниз на percent

        new TouchAction<>(getDriver())
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    /**
     * Скролл к элементу с указанным текстом
     */
    public static void scrollToText(String text) {
        getDriver().findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                        "new UiSelector().textContains(\"" + text + "\"))"));
    }

}
