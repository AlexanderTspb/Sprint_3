// импортируем RestAssured
// импортируем Response

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class scooteAcceptOrderTest {

    private OrderClient orderClient;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();
    }

    @Test
    @Description("Принятие заказа")
    @DisplayName("Принятие заказа с корректными параметрами")
    public void acceptOrderWhenCorrectParametersTest(){

        Courier courier = Courier.getRandom();
        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        courierClient.create(courier);

        //запрос на логин курьера,чтобы получить ID курьера
        Response responseLogin =  courierClient.login(LoginRequestBody.from(courier));

        //получаем ID курьера
        int courierId = responseLogin.jsonPath().getInt("id");

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
        responseFiveth.then().assertThat().body("ok",equalTo(true))
                .and()
                .statusCode(200);

        courierClient.deleteS(courier);

    }

}
