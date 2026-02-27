package ru.kuz.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;

/**
 * Главная страница с видео
 */
public class MainPage {
    private static final Logger log = LoggerFactory.getLogger(MainPage.class);

    private ElementsCollection videoTitles = $$(MobileBy.id("com.vk.vkvideo:id/title"));
    private SelenideElement firstVideoTitle = $(MobileBy.xpath("(//*[@resource-id='com.vk.vkvideo:id/title'])[2]"));

    @Step("Ожидаем загрузки главной страницы")
    public MainPage waitForPageLoaded() {
        log.info("Ожидаем загрузки главной страницы");
        firstVideoTitle.should(Condition.visible, Duration.ofSeconds(15));
        log.info("Главная страница загружена");
        return this;
    }

    @Step("Получаем название первого видео")
    public String getFirstVideoTitle() {
        String text = firstVideoTitle.should(Condition.visible).getText();
        log.info("название первого видео {}", text);
        return text;
    }

    @Step("Получаем название видео по индексу {index}")
    public String getVideoTitleByIndex(int index) {
        String text = videoTitles.get(index).should(Condition.visible).getText();
        log.info("название видео - {} по индексу - {}", text, index);
        return text;
    }

    @Step("Открываем первое видео")
    public VideoPlayerPage openFirstVideo() {
        log.info("Открываем первое видео: {}", firstVideoTitle.should(Condition.visible).getText());
        firstVideoTitle.should(Condition.visible).click();
        return new VideoPlayerPage();
    }

    @Step("Открываем видео по индексу {index}")
    public VideoPlayerPage openVideoByIndex(int index) {
        log.info("Открываем видео по индексу {}", index);
        videoTitles.get(index).should(Condition.visible).click();
        return new VideoPlayerPage();
    }

    @Step("Проверяем что видео найдены на странице")
    public MainPage verifyVideosExist() {
        log.info("Проверяем что видео найдены на странице");
        videoTitles.shouldHave(com.codeborne.selenide.CollectionCondition.sizeGreaterThan(0));
        return this;
    }

}
