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

public class scooterLoginCourierTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }
    @Test
    @Description("Логин курьера")
    @DisplayName("Логин курьера в системе с корректными параметрами")
    public void loginCourierWhenCorrectParametersTest(){

        Courier courier = Courier.getRandom();
        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        courierClient.create(courier);

        //отправляем запрос на логин созданного курьера
        Response responseLogin =  courierClient.login(LoginRequestBody.from(courier));


        responseLogin.then().assertThat().body("id", notNullValue())
                        .and()
        .statusCode(200);
        courierClient.deleteS(courier);
    }

}
