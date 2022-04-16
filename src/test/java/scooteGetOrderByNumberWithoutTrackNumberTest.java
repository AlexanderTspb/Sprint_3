// импортируем RestAssured
// импортируем Response

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class scooteGetOrderByNumberWithoutTrackNumberTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
    @Test
    @Description("Получение заказа по номеру трека без номера трека")
    @DisplayName("Получение заказа по номеру трека без подстановки t")

    public void getOrderByTrackNumberWithoutTrackNumberTest(){

        // по номеру трека получаем номер заказа
        Response responseFourth=  given()
                .header("Content-type", "application/json")
         //       .and()
        //        .queryParam("t", orderTrack)
                .get("/api/v1/orders/track");
        responseFourth.then().assertThat().body("message", equalTo("Недостаточно данных для поиска"))
                .and()
                .statusCode(400);
        System.out.println("Ответ на запрос получения номера заказа" + responseFourth.body().asString());

    }

}
