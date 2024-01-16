package ru.netology;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AppCardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldSuccessDelivery() {
        open("http://localhost:9999");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Курск");
        $(By.cssSelector("[data-test-id='name'] input")).setValue("Корзун Евгений");
        $(By.cssSelector("[data-test-id='phone'] input")).setValue("+79991234567");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $(By.cssSelector("[data-test-id='date'] input")).sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), (Keys.DELETE));
        $(By.cssSelector("[data-test-id='date'] input")).setValue(planningDate);
        $(By.cssSelector("[data-test-id='agreement']")).click();
        $(By.cssSelector("button.button")).click();
        $(By.cssSelector(".notification__content"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }
}