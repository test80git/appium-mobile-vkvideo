package ru.kuz.pages;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class VideoPlayerPage {
    private static final Logger log = LoggerFactory.getLogger(VideoPlayerPage.class);

    private SelenideElement seekBar = $(MobileBy.id("com.vk.vkvideo:id/seek_bar"));
    private SelenideElement videoContainer = $(MobileBy.id("com.vk.vkvideo:id/vk_video_minimizable_player_container"));

    private SelenideElement soundButton = $(By.id("com.vk.vkvideo:id/sound_control"));

    private AndroidDriver getDriver() {
        return (AndroidDriver) getWebDriver();
    }

    @Step("Ожидаем загрузки плеера")
    public VideoPlayerPage waitForPlayerLoaded() {
        log.info("Ожидаем загрузки плеера");
        videoContainer.should(Condition.visible, Duration.ofSeconds(15));
        sleep(3000);
        return this;
    }

    @Step("Тапаем в верхней части экрана (height/4)")
    public VideoPlayerPage tapOnTop() {
        log.info("Тапаем в верхней части экрана");
        Dimension size = getDriver().manage().window().getSize();
        new TouchAction<>(getDriver())
                .tap(PointOption.point(size.width / 2, size.height / 4))
                .perform();
        return this;
    }

    @Step("Тапаем в центре видео (height/3)")
    public VideoPlayerPage tapOnVideo() {
        log.info("Тапаем в центре видео");
        Dimension size = getDriver().manage().window().getSize();
        new TouchAction<>(getDriver())
                .tap(PointOption.point(size.width / 2, size.height / 3))
                .perform();
        return this;
    }

    @Step("Проверяем что время увеличилось")
    public VideoPlayerPage verifyTimeIncreasing() {
        log.info("Проверяем что время увеличилось");

        // ПЕРВЫЙ ТАП вверху (height/4) - чтобы увидеть время
        tapOnTop();

        // Получаем начальное время
        String time1 = seekBar.should(Condition.visible).getAttribute("text");
        log.info("Время начала: {}", time1);

        // Ждем 5 секунд
        log.info("Ждем 2 секунд...");
        sleep(2000);

        // ВТОРОЙ ТАП вверху (height/4) - чтобы снова увидеть время
        tapOnTop();

        // Получаем конечное время
        String time2 = seekBar.should(Condition.visible).getAttribute("text");
        log.info("Время сейчас: {}", time2);

        // Проверяем
        if (time1.equals(time2)) {
            throw new AssertionError("Время не изменилось: " + time1 + " -> " + time2);
        }

        log.info("✅ Время изменилось: {} -> {}", time1, time2);
        return this;
    }

    @Step("Делаем паузу")
    public VideoPlayerPage wait(int seconds) {
        sleep(seconds * 1000L);
        return this;
    }

    @Step("Проверяем, что звук меняется")
    public VideoPlayerPage soundClick() {
        log.info("Проверяем что звук меняется");
        // Получить текущее состояние (через content-desc)
        String state = soundButton.getAttribute("content-desc");
        boolean isMuted = "Mute".equals(state);
        boolean isUnMuted = "Unmute".equals(state);
        log.debug("Звук Включен {}", isMuted);

        // Кликнуть
        soundButton.click();

        if (isMuted) {
            // Проверить, что состояние изменилось
            soundButton.shouldHave(attribute("content-desc", "Unmute"));  // или "Unmute"
            log.info("Звук Выключили");
            Allure.step("Звук Выключили");
        }
        if (isUnMuted) {
            soundButton.shouldHave(attribute("content-desc", "Mute"));  // или "Unmute"
            log.info("Звук Включили");
            Allure.step("Звук Включили");
        }
        return this;
    }


    /**
     * Проверяет, загрузилось ли видео (по наличию кнопки звука)
     * @return true - видео загружено (кнопка звука есть), false - видео не загружено
     */
    @Step("Проверяем, загрузилось ли видео")
    public boolean isVideoLoaded() {
        try {
            log.info("Проверяем, загрузилось ли видео по наличию кнопки звука");
            // Ждем появления кнопки звука максимум 5 секунд
            boolean exists = soundButton.exists();
            log.info("Кнопка звука {}", exists ? "присутствует" : "отсутствует");
            return exists;
        } catch (Exception e) {
            log.error("Ошибка при проверке загрузки видео: {}", e.getMessage());
            return false;
        }
    }

}