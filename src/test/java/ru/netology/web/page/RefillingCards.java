package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RefillingCards {
    private SelenideElement sumField = $("[class=input__control]");
    private SelenideElement cardField = $("[type=tel]");
    private SelenideElement refillingButton = $("[class=button__text]");


    public RefillingCards refilling(String sum, DataHelper.CardsInfo cardsInfo, int id) {
        sumField.setValue(sum);
        if (id != 1) {
            cardField.setValue(cardsInfo.getFirst());
        } else {
            cardField.setValue(cardsInfo.getSecond());
        }
        refillingButton.click();
        return new RefillingCards();
    }

    public void errorIfTransferIsMoreThenBalance() {
        SelenideElement form = $(".form");
        $(byText("Сумма перевода не может превышать допустимый баланс по карте")).shouldBe(visible, Duration.ofSeconds(10));
    }
}

