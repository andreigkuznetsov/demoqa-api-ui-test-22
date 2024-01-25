package helpers;

import data.TestData;
import models.GenerateTokenResponse;
import models.UserDataRequest;
import models.UserDataResponse;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static data.ApiEndpoints.GENERATE_TOKEN;
import static data.ApiEndpoints.USER_ACCOUNT;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.ApiMethodsSpecs.*;

public class WithUserRegistrationExtension implements BeforeEachCallback {

    public static UserDataResponse registrationResponse;
    public static GenerateTokenResponse generateTokenResponse;
    TestData testData = new TestData();

    @Override
    public void beforeEach(ExtensionContext context) {
        if (context.getElement().isPresent() && context.getElement().get() instanceof java.lang.reflect.Method) {
            java.lang.reflect.Method testMethod = (java.lang.reflect.Method) context.getElement().get();

            if (testMethod.isAnnotationPresent(WithUserRegistration.class)) {
                UserDataRequest userData = new UserDataRequest();
                userData.setUserName(testData.userName);
                userData.setPassword(testData.password);

                registrationResponse = step("Отправляем запрос " +
                        "на регистрацию пользователя", () ->
                    given(request)
                        .body(userData)
                        .when()
                        .post(USER_ACCOUNT)
                        .then()
                        .spec(response201)
                        .extract().as(UserDataResponse.class));

                generateTokenResponse = step("Отправляем запрос на генерацию токена", () ->
                        given(request)
                        .body(userData)
                        .when()
                        .post(GENERATE_TOKEN)
                        .then()
                        .spec(response200)
                        .extract().as(GenerateTokenResponse.class));
            }
        }
    }

    public static UserDataResponse getRegistrationResponse() {
        return registrationResponse;
    }

    public static GenerateTokenResponse getGenerateTokenResponse() {
        return generateTokenResponse;
    }
}

