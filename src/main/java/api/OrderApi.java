package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.OrderData;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {
    public static final String CREATE_ORDERS_URI = "/api/v1/orders";
    public static final String GET_ORDERS_URI = "/api/v1/orders";
    public static final String DELETE_ORDERS_URI = "/api/v1/orders/cancel";

    @Step("Create order")
    public ValidatableResponse createOrder (OrderData order){
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDERS_URI)
                .then();
    }

    @Step("Get order")
    public ValidatableResponse getOrders (){
        return given()
                .spec(requestSpecification())
                .when()
                .get(GET_ORDERS_URI)
                .then();
    }

    @Step("Delete order")
    public ValidatableResponse deleteOrders(String track) {
              String requestBody = String.format("{\"track\":\"%s\"}", track);

        return given()
                .spec(requestSpecification())
                .body(requestBody)
                .when()
                .put(DELETE_ORDERS_URI)
                .then();
    }
}


