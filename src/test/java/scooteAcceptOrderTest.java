
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class scooteAcceptOrderTest {

    private OrderClient orderClient;
    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();

        courier = Courier.getRandom();
        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        courierClient.create(courier);
    }

    @After
    public void tearDown() {

        courierClient.deleteS(courier);
        System.out.println("Конец теста");
    }

    @Test
    @Description("Принятие заказа")
    @DisplayName("Принятие заказа с корректными параметрами")
    public void acceptOrderWhenCorrectParametersTest(){

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
        responseFiveth.then().assertThat().statusCode(200)
                .and()
                .body("ok",equalTo(true));

    }

    //2тест
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
        Response responseFiveth = orderClient.acceptOrderWithoutCourierId(orderId);

        responseFiveth.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для поиска"));

    }

    //3 тест

    @Test
    @Description("Принятие заказа без номера заказа")
    @DisplayName("Принятие заказа без подстановки id")
    public void acceptOrderWithoutOrderNumberTest(){

        //запрос на логин курьера,чтобы получить ID курьера
        Response responseLogin =  courierClient.login(LoginRequestBody.from(courier));

        //получаем ID курьера
        int courierId = responseLogin.jsonPath().getInt("id");

        //принимаем заказ

        Response responseFiveth = orderClient.acceptOrderWithoutOrderId(courierId);

        responseFiveth.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для поиска"));


    }

    //4 тест
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

        responseFiveth.then().assertThat().statusCode(404)
                .and()
                .body("message",equalTo("Курьера с таким id не существует"));

    }

    //5 тест

    @Test
    @Description("Принятие заказа с некорректным номером заказа")
    @DisplayName("Принятие заказ с несуществующим id")
    public void acceptOrderWhenUncorrectOrderNumberTest(){

        Response responseLogin =  courierClient.login(LoginRequestBody.from(courier));

        //получаем ID курьера
        int courierId = responseLogin.jsonPath().getInt("id");
        int orderId = RandomUtils.nextInt(1111111111,2000000000);
        //принимаем заказ
        Response responseFiveth =  orderClient.acceptOrder(courierId,orderId);
        responseFiveth.then().assertThat().statusCode(404)
                .and()
                .body("message",equalTo("Заказа с таким id не существует"));

    }
}
