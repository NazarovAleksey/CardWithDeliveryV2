package ru.netology.test;

import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.junit.jupiter.api.*;

import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.getUserInfo;
import static ru.netology.data.DataGenerator.getDateMeeting;

public class CardWithDeliveryTestV2 {

    SelenideElement form;
    String dateMeeting = getDateMeeting(4);
    String dateReplanMeeting = getDateMeeting(6);

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        form = $(".form");
    }

    @Test
    void shouldCardWithDeliverySuccess2() {
        DataGenerator.UserInfo user = getUserInfo();
        form.$("[data-test-id=city] input").setValue(user.getCity());
        form.$("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(dateMeeting);
        form.$("[data-test-id=name] input").setValue(user.getFullName());
        form.$("[data-test-id=phone] input").setValue(user.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + dateMeeting));
        form.$("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(dateReplanMeeting);
        form.$(".button").click();
        $$("span.button__text").find(exactText("Перепланировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + dateReplanMeeting));
    }
}