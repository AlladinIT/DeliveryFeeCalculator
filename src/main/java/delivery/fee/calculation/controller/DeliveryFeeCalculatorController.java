package delivery.fee.calculation.controller;

import delivery.fee.calculation.dto.Input;
import delivery.fee.calculation.dto.Output;
import delivery.fee.calculation.service.DeliveryFeeCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryFeeCalculatorController {

    private final DeliveryFeeCalculatorService deliveryFeeCalculatorService;

    @PostMapping("fee")
    public Output calculateFee(@RequestBody Input input) {
        return deliveryFeeCalculatorService.calculate(input);
    }
}
