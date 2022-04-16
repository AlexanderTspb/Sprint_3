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

public class scooteAcceptOrderWithoutCourierIdTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Description("Принятие заказа без идентификатора курьера")
    @DisplayName("Принятие заказа без подстановки courierId")
    public void acceptOrderWithoutCourierIdTest(){

        //создаем заказ
        Order order = new Order("Naruto","Uchiha","Konoha, 142 apt.",4,
                "+7 800 355 35 35",5,"2020-06-06",
                "Saske, come back to Konoha",new String[]{"BLACK"});

        Response responseThird = orderClient.create(order);

        //получаем номер трека
        int orderTrack = responseThird.jsonPath().getInt("track");

        // по номеру трека получаем номер заказа
        Response responseFourth=  orderClient.getOrderNumber(orderTrack);

        //получаем номер заказа
        int orderId = responseFourth.jsonPath().getInt("order.id");

        //принимаем заказ
        Response responseFiveth =  given()
                .header("Content-type", "application/json")
           //     .and()
           //     .queryParam("courierId", courierId)
                .when()
                .put("https://qa-scooter.praktikum-services.ru/api/v1/orders/accept/" + orderId );

        responseFiveth.then().assertThat().body("message",equalTo("Недостаточно данных для поиска"))
                .and()
                .statusCode(400);
        System.out.println("Ответ на запрос принятия заказа" + responseFiveth.body().asString());

    }

}
