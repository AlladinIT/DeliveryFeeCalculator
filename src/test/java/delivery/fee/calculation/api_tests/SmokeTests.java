package delivery.fee.calculation.api_tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SmokeTests {

    public static final String FEE_CALCULATOR_API_URL = "http://localhost:8080/api/calculate/fee";


    @Test
    public void givenInputDataWithTwoParameters_whenDeliveryFeeCalculatorIsCalled_thenReturnHttp200() {

        String input = """
                {
                "city":"Pärnu",
                "vehicleType":"car"
                }""";

        given()
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
                .body(input)
                .when()
                .post(FEE_CALCULATOR_API_URL)
                .then()
                .statusCode(200);
    }

    @Test
    public void givenInputDataWithThreeParameters_whenDeliveryFeeCalculatorIsCalled_thenReturnHttp200() {

        String input = """
                {
                "city":"Pärnu",
                "vehicleType":"car",
                "dateTime":"2022-08-08 16:16:07"
                }""";

        given()
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
                .body(input)
                .when()
                .post(FEE_CALCULATOR_API_URL)
                .then()
                .statusCode(200);
    }

}
