package delivery.fee.calculation.api_tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeliveryFeeCalculatorTests {
    public static final String FEE_CALCULATOR_API_URL = "http://localhost:8080/api/calculate/fee";

    public static final String errorBecauseOfVehicle1 = """
            {
                "city":"Pärnu",
                "vehicleType":"scooter",
                "dateTime":"2023-01-01 16:16:07"
            }"""; //For that period, there was a 'thunder' in Pärnu
    public static final String errorBecauseOfVehicle2 = """
            {
                "city":"Pärnu",
                "vehicleType":"bike",
                "dateTime":"2023-01-01 16:16:07"
            }""";//For that period, there was a 'thunder' in Pärnu
    public static final String errorBecauseOfVehicle3 = """
            {
                "city":"Tartu",
                "vehicleType":"bike",
                "dateTime":"2023-01-01 16:16:07"
            }""";//For that period, there was a '22 m/s wind speed' in Tartu


    public static final String valid1 = """
            {
                "city":"Tallinn",
                "vehicleType":"car",
                "dateTime":"2023-01-01 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 4 + 0 + 0 + 0 = 4 €


    public static final String valid2 = """
            {
                "city":"TARTU",
                "vehicleType":"Car",
                "dateTime":"2023-01-01 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 3.5 + 0 + 0 + 0 = 3.5 €


    public static final String valid3 = """
            {
                "city":"Pärnu",
                "vehicleType":"car",
                "dateTime":"2023-01-01 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 3 + 0 + 0 + 0 = 3 €

    public static final String valid4 = """
            {
                "city":"Tallinn",
                "vehicleType":"bike",
                "dateTime":"2023-01-01 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 3 + 0.5 + 0.5 + 1 = 5 €


    public static final String valid5 = """
            {
                "city":"Tallinn",
                "vehicleType":"scooter",
                "dateTime":"2023-01-01 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 3.5 + 0.5 + 0 + 1 = 5 €


    public static final String valid6 = """
            {
                "city":"TARTU",
                "vehicleType":"scooter",
                "dateTime":"2023-01-01 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 3 + 0.5 + 0 + 1 = 4.5 €


    public static final String valid7 = """
            {
                "city":"Tallinn",
                "vehicleType":"car",
                "dateTime":"2022-08-08 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 4 + 0 + 0 + 0 = 4 €


    public static final String valid8 = """
            {
                "city":"TARTU",
                "vehicleType":"Car",
                "dateTime":"2022-08-08 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 3.5 + 0 + 0 + 0 = 3.5 €


    public static final String valid9 = """
            {
                "city":"Pärnu",
                "vehicleType":"car",
                "dateTime":"2022-08-08 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 3 + 0 + 0 + 0 = 3 €

    public static final String valid10 = """
            {
                "city":"Tallinn",
                "vehicleType":"bike",
                "dateTime":"2022-08-08 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 3 + 0 + 0 + 0.5 =3.5 €


    public static final String valid11 = """
            {
                "city":"TARTU",
                "vehicleType":"bike",
                "dateTime":"2022-08-08 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 2.5 + 0 + 0 + 0 = 2.5 €


    public static final String valid12 = """
            {
                "city":"Pärnu",
                "vehicleType":"bike",
                "dateTime":"2022-08-08 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 2 + 0 + 0 + 0 = 2 €

    public static final String valid13 = """
            {
                "city":"TaLLinn",
                "vehicleType":"scooter",
                "dateTime":"2022-08-08 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 4 + 0 + 0 + 0 = 4 €


    public static final String valid14 = """
            {
                "city":"TARTU",
                "vehicleType":"SCOOTER",
                "dateTime":"2022-08-08 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 3 + 0 + 0 + 0 = 3 €


    public static final String valid15 = """
            {
                "city":"PäRNu",
                "vehicleType":"SCOOTER",
                "dateTime":"2022-08-08 16:16:07"
            }""";//RBF + ATEF + WSEF + WPEF = 2.5 + 0 + 0 + 0 = 2.5 €
    public static final String nullValue1 = """
            {
                "city":null,
                "vehicleType":"SCOOTER",
                "dateTime":"2022-08-08 16:16:07"
            }""";
    public static final String nullValue2 = """
            {
                "city":"Tallinn",
                "vehicleType":null,
                "dateTime":"2022-08-08 16:16:07"
            }""";
    public static final String nullValue3 = """
            {
            }""";
    public static final String invalidCity1 = """
            {
                "city":"London",
                "vehicleType":"SCOOTER",
                "dateTime":"2022-08-08 16:16:07"
            }""";
    public static final String invalidCity2 = """
            {
                "city":"TTAALLIINN",
                "vehicleType":"SCOOTER",
                "dateTime":"2022-08-08 16:16:07"
            }""";
    public static final String invalidCity3 = """
            {
                "city":123456789,
                "vehicleType":"SCOOTER",
                "dateTime":"2022-08-08 16:16:07"
            }""";
    public static final String invalidVehicle1 = """
            {
                "city":"Tallinn",
                "vehicleType":"BUS",
                "dateTime":"2022-08-08 16:16:07"
            }""";
    public static final String invalidVehicle2 = """
            {
                "city":"Tartu",
                "vehicleType":"Ship",
                "dateTime":"2022-08-08 16:16:07"
            }""";
    public static final String invalidVehicle3 = """
            {
                "city":"PäRNu",
                "vehicleType":123456789,
                "dateTime":"2022-08-08 16:16:07"
            }""";
    public static final String tooOldDate1 = """
            {
                "city":"TaLLinn",
                "vehicleType":"scooter",
                "dateTime":"1980-08-08 16:16:07"
            }""";
    public static final String tooOldDate2 = """
            {
                "city":"TARTU",
                "vehicleType":"SCOOTER",
                "dateTime":"2017-08-08 16:16:07"
            }""";
    public static final String tooOldDate3 = """
            {
                "city":"PäRNu",
                "vehicleType":"SCOOTER",
                "dateTime":"2021-12-12 16:16:07"
            }""";

    static Stream<Arguments> provideValidInputAndCalculatedFee() {
        return Stream.of(
                Arguments.of(valid1, 4.0F),
                Arguments.of(valid2, 3.5F),
                Arguments.of(valid3, 3.0F),
                Arguments.of(valid4, 5.0F),
                Arguments.of(valid5, 5.0F),
                Arguments.of(valid6, 4.5F),
                Arguments.of(valid7, 4.0F),
                Arguments.of(valid8, 3.5F),
                Arguments.of(valid9, 3.0F),
                Arguments.of(valid10, 3.5F),
                Arguments.of(valid11, 2.5F),
                Arguments.of(valid12, 2.0F),
                Arguments.of(valid13, 4.0F),
                Arguments.of(valid14, 3.0F),
                Arguments.of(valid15, 2.5F)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {errorBecauseOfVehicle1, errorBecauseOfVehicle2, errorBecauseOfVehicle3})
    public void givenInputWithSpecificInput_whenDeliveryFeeCalculatorIsCalled_thenShouldContainErrorAboutUsageOfVehicle(String input) {
        given()
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
                .body(input)
                .when()
                .post(FEE_CALCULATOR_API_URL)
                .then()
                .body("errorMessage", equalTo("Usage of selected vehicle type is forbidden"))
                .body("deliveryFee", equalTo(null));
    }

    @ParameterizedTest
    @MethodSource("provideValidInputAndCalculatedFee")
    public void givenInputWithSpecificDate_whenDeliveryFeeCalculatorIsCalled_thenShouldContainDeliveryFee(String input, Float fee) {
        given()
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
                .body(input)
                .when()
                .post(FEE_CALCULATOR_API_URL)
                .then()
                .body("deliveryFee", equalTo(fee))
                .body("errorMessage", equalTo(null));
    }


    @ParameterizedTest
    @ValueSource(strings = {nullValue1, nullValue2, nullValue3})
    public void givenInputWithNullValues_whenDeliveryFeeCalculatorIsCalled_thenShouldContainErrorAboutNullValues(String input) {
        given()
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
                .body(input)
                .when()
                .post(FEE_CALCULATOR_API_URL)
                .then()
                .body("errorMessage", equalTo("Input is null!"))
                .body("deliveryFee", equalTo(null));

    }


    @ParameterizedTest
    @ValueSource(strings = {invalidCity1, invalidCity2, invalidCity3})
    public void givenInputWithInvalidCity_whenDeliveryFeeCalculatorIsCalled_thenShouldContainErrorAboutInvalidCity(String input) {
        given()
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
                .body(input)
                .when()
                .post(FEE_CALCULATOR_API_URL)
                .then()
                .body("errorMessage", equalTo("You can't deliver food to the chosen city"))
                .body("deliveryFee", equalTo(null));
    }


    @ParameterizedTest
    @ValueSource(strings = {invalidVehicle1, invalidVehicle2, invalidVehicle3})
    public void givenInputWithInvalidVehicleType_whenDeliveryFeeCalculatorIsCalled_thenShouldContainErrorAboutInvalidVehicle(String input) {
        given()
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
                .body(input)
                .when()
                .post(FEE_CALCULATOR_API_URL)
                .then()
                .body("errorMessage", equalTo("You can choose only one vehicle type: Car, Scooter or Bike."))
                .body("deliveryFee", equalTo(null));
    }


    @ParameterizedTest
    @ValueSource(strings = {tooOldDate1, tooOldDate2, tooOldDate3})
    public void givenInputWithTooOldDate_whenDeliveryFeeCalculatorIsCalled_thenShouldContainErrorAboutInvalidDate(String input) {
        given()
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
                .body(input)
                .when()
                .post(FEE_CALCULATOR_API_URL)
                .then()
                .body("errorMessage", equalTo("There is no data regarding weather conditions for this period of time"))
                .body("deliveryFee", equalTo(null));
    }

}
