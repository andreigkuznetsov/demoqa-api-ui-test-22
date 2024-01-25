package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ProfilePage {
    private SelenideElement displayUserName = $("#userName-value"),
                            deleteBookButton = $("#delete-record-undefined"),
                            confirmActionModal = $("#closeSmallModal-ok"),
                            tableWithBooks = $(".rt-tbody");

    @Step("Передаем авторизационные данные в браузер")
    public ProfilePage sendCookiesToBrowser(String userId, String expires, String token) {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        getWebDriver().manage().addCookie(new Cookie("token", token));

        return this;
    }

    @Step("Открываем страницу с профилем пользователя")
    public ProfilePage openProfilePage(String userName) {
        open("/profile");
        displayUserName.shouldHave(text(userName));

        return this;
    }

    @Step("Нажимаем на кнопку удаления книги")
    public ProfilePage clickTheDeleteBookButton() {
        deleteBookButton.click();

        return this;
    }

    @Step("Подтверждаем удаление книги в всплывающих окнах")
    public ProfilePage confirmActionInModal() {
        confirmActionModal.click();
        Selenide.confirm();

        return this;
    }

    @Step("Проверяем, что список книг пользователя пустой")
    public ProfilePage checkUserListOfBooks() {
        tableWithBooks.shouldBe(empty);

        return this;
    }

}
