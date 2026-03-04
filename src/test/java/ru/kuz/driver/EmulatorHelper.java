package ru.kuz.driver;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

/**
 * Класс помощник для Page страниц
 */
public class EmulatorHelper {

    private static AndroidDriver getDriver() {
        return (AndroidDriver) WebDriverRunner.getWebDriver();
    }

    /**
     * Нажимает кнопку назад
     */
    public static void goBack() {
        getDriver().navigate().back();
    }

    /**
     * КЛИКАЕТ по элементу с точным совпадением текста
     *
     * @param text точный текст элемента
     */
    public static void clickByExactText(String text) {
        By selector = By.xpath("//*[@text='" + text + "']");
        getDriver().findElement(selector).click();
    }

    /**
     * КЛИКАЕТ по элементу, содержащему указанный текст
     *
     * @param text текст элемента для скролла
     */
    public static void clickByPartialText(String text) {
        getDriver().findElement(By.xpath(
                "//*[contains(@text, '" + text + "')]"
        )).click();
    }

    /**
     * Закрывает клавиатуру если она есть
     */
    public static void closeKeyBoard() {
        if (getDriver().isKeyboardShown()) {
            getDriver().hideKeyboard();
        }
    }

    /**
     * Вводит текст и нажимает Enter
     *
     * @param element поле для ввода
     * @param text    текст
     */
    public static void sendKeysAndFind(SelenideElement element, String text) {
        element.sendKeys(text);
        getDriver().pressKey(new KeyEvent(AndroidKey.ENTER));
    }

}
