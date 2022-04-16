// импортируем RestAssured
// импортируем Response

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class scooteAcceptOrderWithUncorrectCourierIdTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Description("Принятие заказа c некорректным идентификатором курьера")
    @DisplayName("Принятие заказа c несуществующим courierId")
    public void acceptOrderWhenUncorrectCourierIdTest(){

        int courierId = RandomUtils.nextInt(1111111111,2000000000);

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
        Response responseFiveth =  orderClient.acceptOrder(courierId,orderId);

        responseFiveth.then().assertThat().body("message",equalTo("Курьера с таким id не существует"))
                .and()
                .statusCode(404);

    }

}
