package api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.response.ValidatableResponse;
import model.CourierDataLombok;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class CourierApi extends RestApi {

    private static final String CREATE_COURIER_URI = "/api/v1/courier";
    private static final String LOGIN_COURIER_URI = "/api/v1/courier/login";
    private static final String DELETE_COURIER_URI = "/api/v1/courier/{id}";


    @Step("Create a new courier")
    public ValidatableResponse createCourierLombok(CourierDataLombok courier) {
        return given()
                .spec(requestSpecification())
                .body(courier)
                .when()
                .post(CREATE_COURIER_URI)
                .then();
    }
