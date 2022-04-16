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

public class scooteAcceptOrderWithoutOrderNumberTest {

    private OrderClient orderClient;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();
    }

    @Test
    @Description("Принятие заказа без номера заказа")
    @DisplayName("Принятие заказа без подстановки id")
    public void acceptOrderWithoutOrderNumberTest(){

        Courier courier = Courier.getRandom();
        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        courierClient.create(courier);

        //запрос на логин курьера,чтобы получить ID курьера
        Response responseLogin =  courierClient.login(LoginRequestBody.from(courier));

        //получаем ID курьера
        int courierId = responseLogin.jsonPath().getInt("id");

        //принимаем заказ
        Response responseFiveth =  given()
                .header("Content-type", "application/json")
            //    .and()
            //    .queryParam("courierId", courierId)
                .when()
                .put("https://qa-scooter.praktikum-services.ru/api/v1/orders/accept/courierId" + courierId);

        responseFiveth.then().assertThat().body("message",equalTo("Недостаточно данных для поиска"))
                .and()
                .statusCode(400);
        System.out.println("Ответ на запрос принятия заказа" + responseFiveth.body().asString());
        courierClient.deleteS(courier);

    }

}
