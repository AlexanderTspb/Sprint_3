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

public class scooterDeleteCourierTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @Description("Удаление курьера")
    @DisplayName("Удаление курьера с корректными параметрами")
    public void deleteCourierWhenCorrectParametersTest(){

        Courier courier = Courier.getRandom();
        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        Response response =  courierClient.create(courier);

        Response responseDelete = courierClient.deleteS(courier);
        responseDelete.then().assertThat().body("ok", equalTo(true)).
                and()
                .statusCode(200);
    }

}
