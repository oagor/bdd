package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginInput = $("[data-test-id='login'] input");
    private final SelenideElement passwordInput = $("[data-test-id='password'] input");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");

    public LoginPage() {
        loginInput.shouldBe(visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginInput.setValue(info.getLogin());
        passwordInput.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}