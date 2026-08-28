package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cards = $$(".list__item div");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private final SelenideElement header = $("[data-test-id='dashboard']");

    public DashboardPage() {
        header.shouldBe(visible, Duration.ofSeconds(10));
    }

    private SelenideElement getCard(DataHelper.CardInfo cardInfo) {
        return cards.find(attribute("data-test-id", cardInfo.getTestId()));
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        String text = getCard(cardInfo).text();
        return extractBalance(text);
    }

    public TransferPage selectCard(DataHelper.CardInfo cardInfo) {
        getCard(cardInfo).$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value.trim());
    }
}