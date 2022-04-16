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

public class scooteAcceptOrderWithUncorrectOrderNumberTest {

    private OrderClient orderClient;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();
    }

    @Test
    @Description("Принятие заказа с некорректным номером заказа")
    @DisplayName("Принятие заказ с несуществующим id")
    public void acceptOrderWhenUncorrectOrderNumberTest(){

        Courier courier = Courier.getRandom();
        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        courierClient.create(courier);

        Response responseLogin =  courierClient.login(LoginRequestBody.from(courier));

        //получаем ID курьера
        int courierId = responseLogin.jsonPath().getInt("id");
        int orderId = RandomUtils.nextInt(1111111111,2000000000);
        //принимаем заказ
        Response responseFiveth =  orderClient.acceptOrder(courierId,orderId);
        responseFiveth.then().assertThat().body("message",equalTo("Заказа с таким id не существует"))
                .and()
                .statusCode(404);
        courierClient.deleteS(courier);

    }

}
