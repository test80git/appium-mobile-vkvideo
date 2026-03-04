package ru.kuz.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Description;
import ru.kuz.pages.MainPage;
import ru.kuz.pages.VideoPlayerPage;

/**
 * Тесты для VK Video
 */
public class VkVideoTests extends BaseTest {

    private MainPage mainPage;
    private VideoPlayerPage playerPage;

    @BeforeEach
    public void initPages() {
        mainPage = new MainPage();
        playerPage = new VideoPlayerPage();
    }

    @Test
    @Description("Позитивный тест: Видео успешно воспроизводится")
    public void testVideoPlaybackPositive() {
        // ШАГ 1: Открываем видео
        mainPage.waitForPageLoaded()
                .openFirstVideo();

        // ШАГ 2: Ждем загрузки плеера
        playerPage.waitForPlayerLoaded();
        // ШАГ 3: Тапаем по видео (появляются кнопки)
        playerPage.tapOnVideo();
        // ШАГ 4: Ждем появления кнопок
        playerPage.wait(5);
        // ШАГ 5: Тап вверху (скрываем кнопки)
        playerPage.tapOnTop();
        playerPage.wait(2);
        // ШАГ 6: Проверяем что время увеличивается
        playerPage.verifyTimeIncreasing();
    }

    @Test
    @Description("Позитивный тест: Звук работает")
    public void testSoundControl(){
        // ШАГ 1: Открываем видео
        mainPage.waitForPageLoaded();

        playerPage.soundClick();
    }

}
