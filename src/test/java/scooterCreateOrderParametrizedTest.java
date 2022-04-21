import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import io.restassured.RestAssured;
import org.junit.Before;


@RunWith(Parameterized.class)
public class scooterCreateOrderParametrizedTest {

    private OrderClient orderClient;
    private final String[] checkingValue;
    private final int expectedValue;

    public scooterCreateOrderParametrizedTest(String[] checkingValue, int expectedValue){
        this.checkingValue = checkingValue;
        this.expectedValue = expectedValue;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {new String[]{"BLACK"},201 },
                {new String[]{"BLACK","GREY"}, 201},
                {new String[]{null}, 201},
                {new String[]{"GREY"}, 201},
        };
    }

    @Test
    @Description("Создание заказа параметризованный тест по цвету")
    @DisplayName("Создание заказа с подстановкой разных color")

    public void createOrderStatusCodeSuccessAndNotNullValueTrackTest()  {

        Order order = new Order("Itachi","Uchiha","Konoha, 142 apt.",4,
                "+7 777 777 77 77",5,"2020-06-06",
                "Saske, come back to Konoha",checkingValue);

        Response response = orderClient.create(order);
        int actualCode = response.getStatusCode();
        int actualTrack = response.jsonPath().getInt("track");
        Assert.assertNotNull(actualTrack);
        assertEquals(expectedValue,actualCode);

    }

}