package tests;

import data.TestData;
import helpers.WithUserDeleting;
import helpers.WithUserRegistration;
import models.AddListOfBooksRequest;
import models.AddListOfBooksResponse;
import models.ListOfIsbns;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import java.util.List;

import static data.ApiEndpoints.BOOKS;
import static helpers.WithUserRegistrationExtension.getGenerateTokenResponse;
import static helpers.WithUserRegistrationExtension.getRegistrationResponse;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.ApiMethodsSpecs.request;
import static specs.ApiMethodsSpecs.response201;

@DisplayName("API тесты demoqa.com WebShop")
@Tag("all-tests")
public class ProfilePageTests extends TestBase {

        TestData testData = new TestData();
        ProfilePage profilePage = new ProfilePage();

        @Test
        @WithUserRegistration
        @WithUserDeleting
        @DisplayName("Проверка успешного удаления книги из профиля пользователя")
        void removeBookFromUserProfileTest() {
                AddListOfBooksRequest booksToAdd = new AddListOfBooksRequest();
                ListOfIsbns isbns = new ListOfIsbns();
                isbns.setIsbn(testData.isbn);
                booksToAdd.setCollectionOfIsbns(List.of(isbns));
                booksToAdd.setUserId(getRegistrationResponse().getUserId());

                AddListOfBooksResponse addListOfBooksResponse = step("Отправляем запрос " +
                        "на добавление пользователю случайной книги ", () ->
                     given(request)
                        .header("Authorization", "Bearer " + getGenerateTokenResponse().getToken())
                        .body(booksToAdd)
                        .when()
                        .post(BOOKS)
                        .then()
                        .spec(response201)
                        .extract().as(AddListOfBooksResponse.class));

                step("Проверяем, что ответ содержит isbn добавленной книги", () -> {
                        assertThat(addListOfBooksResponse.getBooks())
                                .extracting(ListOfIsbns::getIsbn)
                                .containsExactly(testData.isbn);
                });

                profilePage.sendCookiesToBrowser(getRegistrationResponse().getUserId(),
                                        getGenerateTokenResponse().getExpires(),
                                        getGenerateTokenResponse().getToken())
                        .openProfilePage(getRegistrationResponse().getUserName())
                        .clickTheDeleteBookButton()
                        .confirmActionInModal()
                        .checkUserListOfBooks();
        }
}
