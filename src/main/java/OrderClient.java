import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "api/v1/orders";

    @Step
    public Response create(Order order) {
        Response response =  given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH);
        printResponseBodyToConsole(response,"запрос на создание заказа");
        return response;
    }

    @Step
    public Response getOrderNumber(int orderTrack) {
        Response response = given()
                .spec(getBaseSpec())
                .queryParam("t", orderTrack)
                .get(ORDER_PATH + "/track");
        printResponseBodyToConsole(response,"запрос на получение номера заказа");
        return response;
    }

    @Step
    public Response getOrderNumberWithoutTrackNumber() {
        Response response = given()
                .spec(getBaseSpec())
         //       .queryParam("t", orderTrack)
                .get(ORDER_PATH + "/track");
        printResponseBodyToConsole(response,"запрос на получение номера заказа");
        return response;
    }

    @Step
    public Response acceptOrder(int courierId, int orderId) {
        Response response = given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put(ORDER_PATH + "/accept/" + orderId);
        printResponseBodyToConsole(response,"запрос на принятие заказа");
        return response;
    }

    @Step
    public Response acceptOrderWithoutCourierId(int orderId) {

        Response response = given()
                .spec(getBaseSpec())
             //   .queryParam("courierId", courierId)
                .when()
                .put(ORDER_PATH + "/accept/" + orderId);
        printResponseBodyToConsole(response,"запрос на принятие заказа");
        return response;
    }

    @Step
    public Response acceptOrderWithoutOrderId(int courierId) {

        Response response = given()
                .spec(getBaseSpec())
            //    .queryParam("courierId", courierId)
                .when()
                .put(ORDER_PATH + "/accept/courierId" + courierId);
        printResponseBodyToConsole(response,"запрос на принятие заказа");
        return response;
    }


    @Step
    public Response getOrderList(int courierId) {
        Response response = given()
                    .spec(getBaseSpec())
                    .queryParam("courierId", courierId)
                    .get(ORDER_PATH);
        printResponseBodyToConsole(response,"запрос на получение списка заказов");
        return response;

    }

    @Step("Print response body to console")
    public void printResponseBodyToConsole(Response response, String requestName){
        System.out.println( "Ответ на " + requestName + response.body().asString());
    }
}
