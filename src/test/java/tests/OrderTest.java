package tests;

import api.OrderApi;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import model.OrderData;
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

    public OrderTest(List<String> colors) {

        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {Collections.emptyList()},         // ни один цвет не указан
                {List.of("BLACK")},          // цвет BLACK указан
                {List.of("GREY")},           // цвет GREY указан
                {Arrays.asList("BLACK", "GREY")}   // оба цвета указаны
        };
    }

    @Test
    @Description("Тест на создание заказа с параметрами цвета.")
    public void createOrderTest() {
        OrderData orderData = new OrderData(colors);

        // Создаем заказ с использованием методов API
        orderApi.createOrder(orderData)
                .assertThat()
                .statusCode(201) // Чтоб заказ был успешно создан
                .body("track", notNullValue()); // Проверка, что track присутствует
    }


    @Test
    @Description("Тест на получение заказа.")
    public void getOrdersTest() {
        ValidatableResponse response = orderApi.getOrders();

        response.log().all()
                .assertThat()
                .statusCode(200)  // Ожидаемый статус ответа
                .body("orders", is(notNullValue()));
    }
}


