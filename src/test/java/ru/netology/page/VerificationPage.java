package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement codeInput = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public VerificationPage() {
        codeInput.shouldBe(visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        codeInput.shouldBe(visible).click();
        codeInput.setValue(verificationCode);
        verifyButton.shouldBe(visible, Condition.enabled).click();
        return new DashboardPage();
    }
}