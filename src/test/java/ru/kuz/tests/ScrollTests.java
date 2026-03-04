package ru.kuz.tests;


import static ru.kuz.driver.EmulatorHelper.clickByPartialText;
import static ru.kuz.helper.ScrollHelper.scrollToText;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.kuz.helper.ScrollHelper;
import ru.kuz.pages.MainPage;
import ru.kuz.pages.VideoPlayerPage;

public class ScrollTests extends BaseTest {

    private MainPage mainPage;
    private VideoPlayerPage playerPage;

    @BeforeEach
    public void initPages() {
        mainPage = new MainPage();
        playerPage = new VideoPlayerPage();
    }


    @Test
    @DisplayName("Позитивный тест: Поиск видео по слову в тексте")
    public void testScrollAndFindVideo() {
        mainPage.waitForPageLoaded();

        // Скролл вниз
        ScrollHelper.scrollDown(0.5);

        // Найти видео по заголовку
        String videoTitle = "Иран";
        scrollToText(videoTitle);

        // Кликнуть на найденное видео
        clickByPartialText(videoTitle);
        playerPage.wait(5).verifyTimeIncreasing();
    }


}
