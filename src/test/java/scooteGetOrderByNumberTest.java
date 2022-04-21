
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class scooteGetOrderByNumberTest {


    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        System.out.println("Конец теста");
    }

    @Test
    @Description("Получение заказа по номеру трека")
    @DisplayName("Получение заказа по номеру трека с корректными параметрами")
    public void getOrderByTrackNumberWhenCorrectParametersTest(){

        //создаем заказ
        Order order = new Order("Naruto","Uchiha","Konoha, 142 apt.",4,
                "+7 800 355 35 35",5,"2020-06-06",
                "Saske, come back to Konoha",new String[]{"BLACK"});

        Response response = orderClient.create(order);

        //получаем номер трека
        int orderTrack = response.jsonPath().getInt("track");

        // по номеру трека получаем номер заказа
        Response responseFourth= orderClient.getOrderNumber(orderTrack);
        responseFourth.then().assertThat().body("order", notNullValue())
                .and()
                .statusCode(200);

    }

    //2 тест

    @Test
    @Description("Получение заказа по номеру трека без номера трека")
    @DisplayName("Получение заказа по номеру трека без подстановки t")

    public void getOrderByTrackNumberWithoutTrackNumberTest(){

        // по номеру трека получаем номер заказа

        Response responseFourth = orderClient.getOrderNumberWithoutTrackNumber();

        responseFourth.then().assertThat().body("message", equalTo("Недостаточно данных для поиска"))
                .and()
                .statusCode(400);

    }

    //3 тест

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
