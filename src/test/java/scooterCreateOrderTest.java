
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class scooterCreateOrderTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Description("Создание заказа")
    @DisplayName("Создание заказа c корректными параметрами")
    public void createOrderWhenCorrectParametersTest(){

        Order order = new Order("Naruto","Uchiha","Konoha, 142 apt.",4,
                "+7 800 355 35 35",5,"2020-06-06",
                "Saske, come back to Konoha",new String[]{"BLACK"});

        Response response = orderClient.create(order);
        response.then().assertThat().statusCode(201)
                .and()
                .body("track", notNullValue());

    }

}
