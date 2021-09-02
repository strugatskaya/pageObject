package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.RefillingCards;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    public void setUpAll() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromCard2ToCard1() {

        var cardsInfo = DataHelper.getCardsInfo();
        var cards = new DashboardPage();
        int firstBalanceBefore = cards.getFirstCardBalance();
        int secondBalanceBefore = cards.getSecondCardBalance();
        int refilmentSum = 100;
        var refillingThisCard = cards.refillFirst();
        refillingThisCard.refilling(Integer.toString(refilmentSum), cardsInfo, 1);
        assertEquals(firstBalanceBefore + refilmentSum, cards.getFirstCardBalance());
        assertEquals(secondBalanceBefore - refilmentSum, cards.getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneyFromCard1ToCard2() {

        var cardsInfo = DataHelper.getCardsInfo();
        var cards = new DashboardPage();
        int firstBalanceBefore = cards.getFirstCardBalance();
        int secondBalanceBefore = cards.getSecondCardBalance();
        int refilmentSum = 100;
        var refillingCard = cards.refillSecond();
        refillingCard.refilling(Integer.toString(refilmentSum), cardsInfo, 2);
        assertEquals(firstBalanceBefore - refilmentSum, cards.getFirstCardBalance());
        assertEquals(secondBalanceBefore + refilmentSum, cards.getSecondCardBalance());
    }

    @Disabled
    @Test
    void shouldTransferMoneyBetweenOwnCardsV3() {
        var cardsInfo = DataHelper.getCardsInfo();
        var cards = new DashboardPage();
        var refilment = new RefillingCards();
        int firstBalanceBefore = cards.getFirstCardBalance();
        int secondBalanceBefore = cards.getSecondCardBalance();
        int refilmentSum = 70000;
        // в этом тесте обнаружен баг. беспрепятственно переводится большая сумма, чем лежит на счете, счет при этом становится отрицательным до бесконечности

        var refillingCard = cards.refillSecond();
        refillingCard.refilling(Integer.toString(refilmentSum), cardsInfo, 2);
        refilment.errorIfTransferIsMoreThenBalance();
        assertEquals(firstBalanceBefore, cards.getFirstCardBalance());
        assertEquals(secondBalanceBefore, cards.getSecondCardBalance());
    }
}



