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

public class scooterLoginCourierWithUncorrectLoginLoginTest {

    private CourierClient courierClient;
    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @Description("Логин курьера c несуществующим логином")
    @DisplayName("Логин курьера с подстановкой несуществующего login")
    public void loginCourierWhenUncorrectParametersTest(){

        Courier courier = Courier.getRandom();
        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        Response response =  courierClient.create(courier);

        String courierLoginSecond = RandomStringUtils.randomAlphabetic(10);
        LoginRequestBody courierBody = LoginRequestBody.from(courier);
        String courierPassword= courierBody.password;

        String loginRequestBody = "{\"login\":\"" + courierLoginSecond + "\","
                + "\"password\":\"" + courierPassword + "\"}";

        // запрос на логин курьера
        Response responseSecond =  given()
                .header("Content-type", "application/json")
                .and()
                .body(loginRequestBody)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier/login");

        responseSecond.then().assertThat().body("message",equalTo("Учетная запись не найдена"))
                        .and()
        .statusCode(404);
        System.out.println("Ответ на запрос логина с несуществующим логином" + responseSecond.body().asString());

        courierClient.deleteS(courier);

    }

}
