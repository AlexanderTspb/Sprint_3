
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class scooterLoginCourierTest {

    private CourierClient courierClient;
    private Courier courier;


    @Before
    public void setUp() {
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
    @Description("Логин курьера")
    @DisplayName("Логин курьера в системе с корректными параметрами")
    public void loginCourierWhenCorrectParametersTest(){

        //отправляем запрос на логин созданного курьера
        Response responseLogin =  courierClient.login(LoginRequestBody.from(courier));

        responseLogin.then().assertThat().statusCode(200)
                .and()
                .body("id", notNullValue());

    }

    //2 тест

    @Test
    @Description("Логин курьера без логина")
    @DisplayName("Логин курьера без подстановки login")
    public void loginCourierWithoutLoginTest(){

        Response responseSecond =  courierClient.loginWithoutLogin(courier);

        responseSecond.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));

    }

    //3 тест

    @Test
    @Description("Логин курьера без пароля")
    @DisplayName("Логин курьера без подстановки password")
    public void loginCourierWithoutPasswordTest(){

        Response responseSecond = courierClient.loginWithoutPassword(courier);

        responseSecond.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));

    }

    //4 тест

    @Test
    @Description("Логин курьера c несуществующим логином")
    @DisplayName("Логин курьера с подстановкой несуществующего login")
    public void loginCourierWhenUncorrectParametersTest(){

        Response responseSecond = courierClient.loginWithUncorrectLogin(courier);

        responseSecond.then().assertThat().statusCode(404)
                .and()
                .body("message",equalTo("Учетная запись не найдена"));

    }

}
