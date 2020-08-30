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

public class CardWithDeliveryTestV2 {

    SelenideElement form;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        form = $(".form");
    }

    @Test
    void shouldCardWithDeliverySuccess() {
        DataGenerator.UserInfo user = getUserInfo();
        form.$("[data-test-id=city] input").setValue(user.getCity());
        form.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(user.getDate());
        form.$("[data-test-id=name] input").setValue(user.getFullName());
        form.$("[data-test-id=phone] input").setValue(user.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$$(".form button").find(text("Запланировать")).click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на"));
        $("[data-test-id=success-notification]").shouldHave(text(user.getDate()));
    }
}
