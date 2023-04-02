package delivery.fee.calculation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Output {
    private BigDecimal deliveryFee;
    private String errorMessage;
}
