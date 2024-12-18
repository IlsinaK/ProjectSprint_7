package tests;

import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierDataLombok;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginCourierTest {

    private CourierApi courierApi;
    private CourierDataLombok courierDataLombok;
    private Integer courierId; // Хранение ID курьера для последующего удаления
    private boolean needAuthorization; // Флаг для контроля авторизации

    @Before
    public void setUp() {
        courierApi = new CourierApi();
        String loginParam = "Vlad" + RandomStringUtils.randomAlphabetic(4);
        courierDataLombok = new CourierDataLombok(loginParam, "passwordVlad1234534", "Vlad");

        ValidatableResponse response = courierApi.createCourierLombok(courierDataLombok);
        response.log().all()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body("ok", is(true));

        courierId = response.extract().path("id");
        needAuthorization = true;
            }

    @After
    public void cleanUp() {
        if (needAuthorization && courierId != null) {
            courierApi.deleteCourier(courierId); // Удаляем курьера после теста
        }
    }

    @DisplayName("Check courier can login")
    @Test
    public void courierCanLoginTest() {
        CourierDataLombok validCourierData = new CourierDataLombok(courierDataLombok.getLogin(), courierDataLombok.getPassword());
        ValidatableResponse loginResponse = courierApi.loginCourier(validCourierData);

        loginResponse.log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("id", notNullValue()); // Проверяем, что id совпадает с id созданного курьера
    }

    @DisplayName("Check that login fails with incorrect credentials")
    @Test
    public void loginFailsWithIncorrectCredentialsTest() {
        CourierDataLombok incorrectCourier = new CourierDataLombok("NonExistentUser", "wrongPassword");
        ValidatableResponse response = courierApi.loginCourier(incorrectCourier);

        response.log().all()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .body("code", is(404))
                .body("message", is("Учетная запись не найдена"));
        needAuthorization = false;
    }

    @DisplayName("Check that login fails with missing login")
    @Test
    public void loginFailsWithMissingLoginTest() {
        ValidatableResponse responseMissingLogin = courierApi.loginCourier(new CourierDataLombok(null, "password"));
        responseMissingLogin.log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("code", is(400))
                .body("message", is("Недостаточно данных для входа"));
        needAuthorization = false;
    }


    @DisplayName("Check that login fails with missing password")
    @Test // Устанавливаем таймаут теста на 10 секунд
    public void passwordFailsWithMissingFieldsTest() {
        // Выполняем запрос и сохраняем начальное время
        ValidatableResponse responseMissingPassword = courierApi.loginCourier(new CourierDataLombok("NonExistentUser", ""));

        // Ждем, пока статус ответа станет 400
        await().atMost(10, SECONDS).untilAsserted(() -> {
            responseMissingPassword.log().all()
                    .assertThat()
                    .statusCode(400)
                    .body("code", is(400))
                    .body("message", is("Недостаточно данных для входа"));
        });

        needAuthorization = false;
    }



    @DisplayName("Check that login fails for non-existent user")
    @Test
    public void loginFailsForNonExistentUserTest() {
        CourierDataLombok nonExistentCourier = new CourierDataLombok("NonExistentUser", "password");
        ValidatableResponse response = courierApi.loginCourier(nonExistentCourier);

        response.log().all()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .body("code", is(404))
                .body("message", is("Учетная запись не найдена"));
        needAuthorization = false;
    }
}
