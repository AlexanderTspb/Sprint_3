
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
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

    @After
    public void tearDown() {
        System.out.println("Конец теста");
    }

    @Test
    @Description("Удаление курьера")
    @DisplayName("Удаление курьера с корректными параметрами")
    public void deleteCourierWhenCorrectParametersTest(){

        Courier courier = Courier.getRandom();
        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        Response response =  courierClient.create(courier);

        Response responseDelete = courierClient.deleteS(courier);
        responseDelete.then().assertThat().statusCode(200)
                .and()
                .body("ok", equalTo(true));
    }

    //2тест

    @Test
    @Description("Удаление курьера без идентификатора курьера")
    @DisplayName("Удаление курьера без подстановки courierId")
    public void deleteCourierWithoutCourierIdTest(){

        // удаление созданного курьера
        Response responseDelete = courierClient.deleteWithoutId();

        responseDelete.then().assertThat().statusCode(404)
                .and()
                .body("message", equalTo("Not Found."));

    }

    //3 тест

    @Test
    @Description("Удаление курьера c несуществующим идентификатором курьера")
    @DisplayName("Удаление курьера c подстановкой несуществующего courierId")
    public void deleteCourierWhenUncorrectParametersTest(){

        String courierId = RandomStringUtils.randomNumeric(7);
        System.out.println("несуществующий идентификатор курьера " + courierId);

        // удаление созданного курьера

        Response responseDelete = courierClient.deleteWithUncorrectId(courierId);

        responseDelete.then().assertThat().statusCode(404)
                .and()
                .body("message", equalTo("Курьера с таким id нет."));

    }

}
