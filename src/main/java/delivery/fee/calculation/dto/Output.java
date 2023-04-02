package delivery.fee.calculation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Output {

    @Schema(
            description = "Calculated delivery fee"
    )
    private BigDecimal deliveryFee;


    @Schema(
            description = "Error message while calculation"
    )
    private String errorMessage;
}
