package model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourierDataLombok {
    private String login;
    private String password;
    private String firstName;
    private Integer id;

    // Конструктор для создания курьера с логином, паролем и именем
    public CourierDataLombok(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    // Конструктор для создания курьера с логином и паролем
    public CourierDataLombok(String login, String password) {
        this.login = login;
        this.password = password;
        this.firstName = null; // Устанавливаем default значением для имени
    }

}
