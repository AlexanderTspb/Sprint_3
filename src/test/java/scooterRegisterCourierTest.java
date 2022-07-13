
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

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.notNullValue;

public class scooterRegisterCourierTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        System.out.println("Конец теста");
    }

    @Test
    @Description("Создание курьера")
    @DisplayName("Создание курьера c корректными параметрами")
    public void registerCourierWhenCorrectParametersTest(){

        courier = Courier.getRandom();

        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        Response response =  courierClient.create(courier);
        response.then().assertThat().statusCode(201)
            .and()
            .body("ok",equalTo(true));
        courierClient.deleteS(courier);
    }

    //2тест

    @Test
    @Description("Создание курьера c уже существующим логином")
    @DisplayName("Создание курьера с подстановкой существующего login")
    public void registerCourierWithExistingLoginTest(){

        courier = Courier.getRandom();

        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        Response response =  courierClient.create(courier);
        Response responseSecond =  courierClient.create(courier);
        responseSecond.then().assertThat().statusCode(409)
                .and()
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));
        courierClient.deleteS(courier);

    }

    // 3 тест

    @Test
    @Description("Создание курьера без имени")
    @DisplayName("Создание курьера без подстановки firstName")
    public void registerCourierWithoutFirstNameTest(){

        Response response = courierClient.createCourierWithoutFirstName();
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok",equalTo(true));

    }

    // 4 тест

    @Test
    @Description("Создание курьера без логина")
    @DisplayName("Создание курьера без подстановки login")
    public void registerCourierWithoutLoginTest(){

        Response response = courierClient.createCourierWithoutLogin();
        response.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));

    }

    // 5 тест

    @Test
    @Description("Создание курьера без пароля")
    @DisplayName("Создание курьера без подстановки password")
    public void registerCourierWithoutPasswordTest(){

        Response response = courierClient.createCourierWithoutPassword();
        response.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));

    }

}
