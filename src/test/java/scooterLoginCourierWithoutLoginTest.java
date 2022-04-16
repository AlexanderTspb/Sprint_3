// импортируем RestAssured
// импортируем Response

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class scooterLoginCourierWithoutLoginTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @Description("Логин курьера без логина")
    @DisplayName("Логин курьера без подстановки login")
    public void loginCourierWithoutLoginTest(){

        Courier courier = Courier.getRandom();
        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        courierClient.create(courier);

        LoginRequestBody courierBody = LoginRequestBody.from(courier);
        String courierPassword = courierBody.password;

        String loginRequestBody = "{\"password\":\"" + courierPassword + "\"}";

        // отправляем запрос на логин курьера
        Response responseSecond =  given()
                .header("Content-type", "application/json")
                .and()
                .body(loginRequestBody)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier/login");

        responseSecond.then().assertThat().body("message",equalTo("Недостаточно данных для входа"))
                        .and()
        .statusCode(400);
        System.out.println("Ответ на запрос логина без указания логина" + responseSecond.body().asString());

        courierClient.deleteS(courier);

    }

}
