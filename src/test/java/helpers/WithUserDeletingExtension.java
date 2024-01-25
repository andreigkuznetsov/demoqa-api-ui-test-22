package helpers;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static data.ApiEndpoints.USER_ACCOUNT;
import static helpers.WithUserRegistrationExtension.getGenerateTokenResponse;
import static helpers.WithUserRegistrationExtension.getRegistrationResponse;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.ApiMethodsSpecs.requestGetDelete;
import static specs.ApiMethodsSpecs.response204;

public class WithUserDeletingExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) {
        if (context.getElement().isPresent() && context.getElement().get() instanceof java.lang.reflect.Method) {
            java.lang.reflect.Method testMethod = (java.lang.reflect.Method) context.getElement().get();

            if (testMethod.isAnnotationPresent(WithUserRegistration.class)) {
                step("Отправляем запрос на удаление пользователя и проверяем Status Code ответа", () ->
                        given(requestGetDelete)
                                .header("Authorization", "Bearer " +
                                        getGenerateTokenResponse().getToken())
                                .when()
                                .delete(USER_ACCOUNT + getRegistrationResponse().getUserId())
                                .then().spec(response204));
            }
        }
    }
}
