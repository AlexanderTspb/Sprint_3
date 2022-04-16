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

public class scooterRegisterCourierExistingLoginTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @Description("Создание курьера c уже существующим логином")
    @DisplayName("Создание курьера с подстановкой существующего login")
    public void registerCourierWithExistingLoginTest(){

        Courier courier = Courier.getRandom();

        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        Response response =  courierClient.create(courier);
        Response responseSecond =  courierClient.create(courier);
        responseSecond.then().assertThat().body("message",equalTo("Этот логин уже используется. Попробуйте другой."))
                        .and()
        .statusCode(409);
        courierClient.deleteS(courier);

    }

}
