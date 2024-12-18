package model;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    @Step("Generate random courier")
    public static CourierDataLombok getRandomCourier() {
        String login = RandomStringUtils.randomAlphabetic(8);
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(8);

        return new CourierDataLombok(login, password, firstName);
    }

   @Step("Generate random courier with parameters")
   public static CourierDataLombok getRandomCourier(String loginParam, String passwordParam,
                                                     String firstNameParam) {
       String login = loginParam + RandomStringUtils.randomAlphabetic(5);
       String password = passwordParam + RandomStringUtils.randomAlphabetic(5);
       String firstName = firstNameParam + RandomStringUtils.randomAlphabetic(5);

       CourierDataLombok courierDataLombok = new CourierDataLombok(login, password, firstName);
       return courierDataLombok;
   }
}

