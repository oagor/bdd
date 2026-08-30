package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        var authInfo = DataHelper.getAuthInfo();

        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);

        var verificationCode = DataHelper.getVerificationCode();

        var dashboardPage = verificationPage.validVerify(verificationCode.getCode());

        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();

        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);

        var amount = 1000;

        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;

        var transferPage = dashboardPage.selectCard(secondCardInfo);

        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);

        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void shouldNotTransferAmountMoreThanBalance() {
        var authInfo = DataHelper.getAuthInfo();

        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);

        var verificationCode = DataHelper.getVerificationCode();

        var dashboardPage = verificationPage.validVerify(verificationCode.getCode());

        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();

        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);

        var amount = firstCardBalance + 5000;

        var transferPage = dashboardPage.selectCard(secondCardInfo);

        transferPage.makeTransfer(String.valueOf(amount), firstCardInfo);

        transferPage.findErrorNotification();
    }
}