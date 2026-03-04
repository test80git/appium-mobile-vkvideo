package ru.kuz.tests;

import static ru.kuz.helper.Constants.SCREENSHOT_TO_SAVE_FOLDER;
import static ru.kuz.helper.DeviceHelper.executeBash;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.MalformedURLException;

import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import ru.kuz.helper.RunHelper;
import ru.kuz.listeners.AllureListener;


/**
 * Базовый тестовый класс
 */
@ExtendWith(AllureListener.class)//расширяем тестовый класс
public class BaseTest {


    @BeforeEach
    public void setup() throws MalformedURLException {
        // Логирование для Allure
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        // папка для сохранения скриншотов selenide
        Configuration.reportsFolder = SCREENSHOT_TO_SAVE_FOLDER;

        //инициализируем андройд драйвер
        Configuration.browser = RunHelper.runHelper().getDriverClass().getName();
        Configuration.browserSize = null;
        Configuration.timeout = 10000;
        disableAnimationOnEmulator();

    }

    /**
     * Отключение анимаций на эмуляторе чтобы не лагало
     */
    private static void disableAnimationOnEmulator() {
        String adbCommand = getAdbCommand();
        executeBash(adbCommand + " shell settings put global transition_animation_scale 0.0");
        executeBash(adbCommand + " shell settings put global window_animation_scale 0.0");
        executeBash(adbCommand + " shell settings put global animator_duration_scale 0.0");
    }

    private static String getAdbCommand() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "adb.exe";  // Windows
        } else {
            return "adb";      // Linux/Mac
        }
    }


    @BeforeEach
    public void startDriver() {
        Allure.step("Открыть приложение", () -> Selenide.open());
    }

    @AfterEach
    public void afterEach() {
        Allure.step("Закрыть приложение", Selenide::closeWebDriver);
    }

}
