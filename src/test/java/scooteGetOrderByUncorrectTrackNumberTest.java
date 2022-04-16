// импортируем RestAssured
// импортируем Response

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class scooteGetOrderByUncorrectTrackNumberTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Description("Получение заказа по номеру трека c некорректным номером трека")
    @DisplayName("Получение заказа по номеру трека с несуществующим t")
    public void getOrderByTrackNumberWhenUncorrectTrackNumberTest(){

        //получаем номер трека
        int orderTrack = RandomUtils.nextInt(55555555,88888888);

        // по номеру трека получаем номер заказа
        Response responseFourth = orderClient.getOrderNumber(orderTrack);
        responseFourth.then().assertThat().body("message", equalTo("Заказ не найден"))
                .and()
                .statusCode(404);

    }

}
