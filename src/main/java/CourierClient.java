import io.qameta.allure.Step;
import io.restassured.response.Response;

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

    @Step("Удаляем курьера {courier}")
    public Response deleteS(Courier courier) {

        Response responseLogin = login(LoginRequestBody.from(courier));
        String courierId = responseLogin.jsonPath().getString("id");
     //   printResponseBodyToConsole(responseLogin,"запрос логина");
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
