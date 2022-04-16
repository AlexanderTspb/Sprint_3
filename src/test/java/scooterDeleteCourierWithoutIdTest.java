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

public class scooterDeleteCourierWithoutIdTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @Description("Удаление курьера без идентификатора курьера")
    @DisplayName("Удаление курьера без подстановки courierId")
    public void deleteCourierWithoutCourierIdTest(){

        // удаление созданного курьера
        Response responseDelete = given()
                .header("Content-type", "application/json")
                .and()
                .delete("https://qa-scooter.praktikum-services.ru/api/v1/courier/");
        responseDelete.then().assertThat().body("message", equalTo("Not Found.")).
                and()
                .statusCode(404);
        System.out.println("Ответ на запрос удаления курьера" + responseDelete.body().asString());

    }

}
