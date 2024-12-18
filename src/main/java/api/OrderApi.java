package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.OrderData;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {
    public static final String CREATE_ORDERS_URI = "/api/v1/orders";
    public static final String GET_ORDERS_URI = "/api/v1/orders";


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
}


