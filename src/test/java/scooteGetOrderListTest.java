
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class scooteGetOrderListTest {

    private OrderClient orderClient;
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;
    private int orderTrack;
    private int orderId;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();

        courier = Courier.getRandom();
        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        courierClient.create(courier);

        Response responseLogin =  courierClient.login(LoginRequestBody.from(courier));

        //получаем ID курьера
        courierId = responseLogin.jsonPath().getInt("id");

        //создаем заказ
        Order order = new Order("Naruto","Uchiha","Konoha, 142 apt.",4,
                "+7 800 355 35 35",5,"2020-06-06",
                "Saske, come back to Konoha",new String[]{"BLACK"});

        Response responseThird = orderClient.create(order);

        //получаем номер трека
        orderTrack = responseThird.jsonPath().getInt("track");

        // по номеру трека получаем номер заказа
        Response responseFourth =  orderClient.getOrderNumber(orderTrack);

        //получаем номер заказа
        orderId = responseFourth.jsonPath().getInt("order.id");

        //принимаем заказ
        orderClient.acceptOrder(courierId,orderId);

    }

    @After
    public void tearDown() {

        courierClient.deleteS(courier);
        System.out.println("Конец теста");
    }

    @Test
    @Description("Получение списка заказов")
    @DisplayName("Получение списка заказов c корректными параметрами")
    public void getOrderListWhenCorrectParametersTest(){

        //получаем список заказов

        Response responseSixth =  orderClient.getOrderList(courierId);
        responseSixth.then().assertThat().statusCode(200)
                .and()
                .body("orders", notNullValue());

    }

}
