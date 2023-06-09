package delivery.fee.calculation.controller;

import delivery.fee.calculation.dto.Input;
import delivery.fee.calculation.dto.Output;
import delivery.fee.calculation.service.DeliveryFeeCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(
        name = "REST API for Delivery fee calculation",
        description = "API - request delivery fee according to the input parameters"
)
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryFeeCalculatorController {

    private final DeliveryFeeCalculatorService deliveryFeeCalculatorService;


    /**
     * This method calculates the delivery fee for food couriers based on regional base fee, vehicle type, and weather
     * conditions. All those factors are stored inside a database.
     * @param input Input class containing City(required), Vehicle type(required) and Date time(not required).
     * @return Output class containing either delivery fee or an error.
     */
    @Operation(
            summary = "Calculate delivery fee",
            description = "Calculate delivery fee according to the input parameters"
    )
    @PostMapping("fee")
    public Output calculateFee(@RequestBody Input input) {
        return deliveryFeeCalculatorService.calculate(input);
    }
}
