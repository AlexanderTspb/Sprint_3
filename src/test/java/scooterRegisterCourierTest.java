// импортируем RestAssured
// импортируем Response

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.notNullValue;

public class scooterRegisterCourierTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @Description("Создание курьера")
    @DisplayName("Создание курьера c корректными параметрами")
    public void registerCourierWhenCorrectParametersTest(){

        Courier courier = Courier.getRandom();

        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        Response response =  courierClient.create(courier);
        response.then().assertThat().body("ok",equalTo(true))
                        .and()
        .statusCode(201);

       courierClient.deleteS(courier);

    }

}
