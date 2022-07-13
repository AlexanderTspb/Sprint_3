import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier";

    @Step("Создаем курьера {courier}")
    public Response create(Courier courier) {
        Response response = given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH);
        printResponseBodyToConsole(response,"запрос регистрации");
        return response;
    }

    @Step("Создаем курьера {courier}")
    public Response createCourierWithoutFirstName() {

        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String courierPassword = RandomStringUtils.randomAlphabetic(10);

        JSONObject loginReqObj = new JSONObject();
        loginReqObj.put("login",courierLogin);
        loginReqObj.put("password",courierPassword);

        Response response = given()
                .spec(getBaseSpec())
                .body(loginReqObj.toString())
                .when()
                .post(COURIER_PATH);
        printResponseBodyToConsole(response,"запрос регистрации");

        LoginRequestBody loginRequestBody = new LoginRequestBody(courierLogin,courierPassword);
        Response responseLogin = login(loginRequestBody);
        String courierId = responseLogin.jsonPath().getString("id");

        Response responseDelete = given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + "/" + courierId);
        printResponseBodyToConsole(responseDelete,"запрос на удаление курьера");

        return response;
    }

    @Step("Создаем курьера {courier}")
    public Response createCourierWithoutLogin() {

        // с помощью библиотеки RandomStringUtils генерируем пароль
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем имя курьера
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        JSONObject loginReqObj = new JSONObject();
        loginReqObj.put("password",courierPassword);
        loginReqObj.put("firstName",courierFirstName);

        Response response = given()
                .spec(getBaseSpec())
                .body(loginReqObj.toString())
                .when()
                .post(COURIER_PATH);
        printResponseBodyToConsole(response,"запрос регистрации");
        return response;
    }

    @Step("Создаем курьера {courier}")
    public Response createCourierWithoutPassword() {

        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем имя курьера
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        JSONObject loginReqObj = new JSONObject();
        loginReqObj.put("password",courierLogin);
        loginReqObj.put("firstName",courierFirstName);

        Response response = given()
                .spec(getBaseSpec())
                .body(loginReqObj.toString())
                .when()
                .post(COURIER_PATH);
        printResponseBodyToConsole(response,"запрос регистрации");
        return response;
    }

    @Step("Авторизуемся курьером {loginRequestBody}")
    public Response login(LoginRequestBody loginRequestBody) {
           Response response = given()
                .spec(getBaseSpec())
                .body(loginRequestBody)
                .when()
                .post(COURIER_PATH + "/login");
        printResponseBodyToConsole(response,"запрос логина");
        return response;
    }

    @Step("Авторизуемся курьером {loginRequestBody}")
    public Response loginWithoutLogin(Courier courier) {

        LoginRequestBody courierBody = LoginRequestBody.from(courier);
        String courierPassword = courierBody.password;

        JSONObject loginReqObj = new JSONObject();
        loginReqObj.put("password",courierPassword);

        Response response = given()
                .spec(getBaseSpec())
                .body(loginReqObj.toString())
                .when()
                .post(COURIER_PATH + "/login");
        printResponseBodyToConsole(response,"запрос логина");
        return response;
    }

    @Step("Авторизуемся курьером {loginRequestBody}")
    public Response loginWithoutPassword(Courier courier) {
        LoginRequestBody courierBody = LoginRequestBody.from(courier);
        String courierLogin= courierBody.login;

        JSONObject loginReqObj = new JSONObject();
        loginReqObj.put("login",courierLogin);

        Response response = given()
                .spec(getBaseSpec())
                .body(loginReqObj.toString())
                .when()
                .post(COURIER_PATH + "/login");
        printResponseBodyToConsole(response,"запрос логина");
        return response;
    }

    @Step("Авторизуемся курьером {loginRequestBody}")
    public Response loginWithUncorrectLogin(Courier courier) {

        String courierLoginSecond = RandomStringUtils.randomAlphabetic(10);
        LoginRequestBody courierBody = LoginRequestBody.from(courier);
        String courierPassword= courierBody.password;

        JSONObject loginReqObj = new JSONObject();
        loginReqObj.put("login",courierLoginSecond);
        loginReqObj.put("password",courierPassword);
        Response response = given()
                .spec(getBaseSpec())
                .body(loginReqObj.toString())
                .when()
                .post(COURIER_PATH + "/login");
        printResponseBodyToConsole(response,"запрос логина");
        return response;
    }

    @Step("Удаляем курьера {courier}")
    public Response deleteS(Courier courier) {

        Response responseLogin = login(LoginRequestBody.from(courier));
        String courierId = responseLogin.jsonPath().getString("id");
        Response response = given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + "/" + courierId);
        printResponseBodyToConsole(response,"запрос на удаление курьера");
        return response;
    }

    @Step("Удаляем курьера {courier}")
    public Response deleteId(int courierId) {

        Response response = given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + "/" + courierId);
        printResponseBodyToConsole(response,"запрос на удаление курьера");
        return response;

    }

    @Step("Удаляем курьера {courier}")
    public Response deleteWithoutId() {

       // Response responseLogin = login(LoginRequestBody.from(courier));
       // String courierId = responseLogin.jsonPath().getString("id");
        Response response = given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + "/");
        printResponseBodyToConsole(response,"запрос на удаление курьера");
        return response;
    }


    @Step("Удаляем курьера {courier}")
    public Response deleteWithUncorrectId(String courierId) {

       // Response responseLogin = login(LoginRequestBody.from(courier));
       // String courierId = responseLogin.jsonPath().getString("id");
        Response response = given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + "/" + courierId);
        printResponseBodyToConsole(response,"запрос на удаление курьера");
        return response;
    }

    @Step("Print response body to console")
    public void printResponseBodyToConsole(Response response, String requestName){
        System.out.println( "Ответ на " + requestName + response.body().asString());
    }

}
