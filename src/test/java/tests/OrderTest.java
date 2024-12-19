package tests;

import api.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.OrderData;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {

    private final List<String> colors;
    private final OrderApi orderApi = new OrderApi();
    private String track;

    public OrderTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {Collections.emptyList()},         // Никакие цвета не указаны
                {List.of("BLACK")},                // Указан цвет BLACK
                {List.of("GREY")},                 // Указан цвет GREY
                {Arrays.asList("BLACK", "GREY")}   // Указаны оба цвета
        };
    }

    @Test
    @DisplayName("A test for creating an order with color parameters")
    @Description("The ability to create orders when choosing different colors")
    public void createOrderTest() {
        OrderData orderData = new OrderData(colors);
        ValidatableResponse response = orderApi.createOrder(orderData);
        response.log().all()
                .assertThat()
                .statusCode(201) // Проверка успешного создания заказа
                .body("track", notNullValue()); // Проверка наличия поля track в ответе

    }

    @Test
    @DisplayName("Test for receiving an order")
    @Description("Check that you can get a list of orders when you request it")
    public void getOrdersTest() {
        // Получение списка заказов
        ValidatableResponse response = orderApi.getOrders();
        response.log().all()
                .assertThat()
                .statusCode(200)  // Проверка полученного статуса
                .body("orders", is(notNullValue())); // Проверка наличия списка заказов
    }


    @After

    public void cleanUpTest() {
        if (track != null) {
            ValidatableResponse deleteResponse = orderApi.deleteOrders(track);
            deleteResponse.log().all()
                    .assertThat()
                    .statusCode(200); // Проверка успешного удаления заказа
        }
    }
}


