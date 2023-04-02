package delivery.fee.calculation.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "extra_fee")
public class ExtraFee {

    @Schema(
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Id
    @SequenceGenerator(
            name = "extra_fee_seq",
            sequenceName = "extra_fee_seq",
            allocationSize = 1,
            initialValue = 19
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "extra_fee_seq"
    )
    Long id;


    @Schema(
            description = "Vehicle type that the fee rule is assigned to",
            example = "Car"
    )
    @Column(name = "vehicle_type")
    private String vehicleType;


    @Schema(
            description = "Type of the fee (ATEF, WSEF or WPEF)",
            example = "ATEF"
    )
    @Column(name = "extra_fee_type")
    private String extraFeeType;

    @Schema(
            description = "Left boundary (inclusive) for ATEF and WSEF, or String parameter for WPEF",
            example = "15"
    )
    @Column(name = "first_parameter")
    private String firstParameter;


    @Schema(
            description = "Right boundary (inclusive) for ATEF and WSEF only",
            example = "20"
    )
    @Column(name = "second_parameter")
    private String secondParameter;

    @Schema(
            description = "Fee value for this fee rule(if fee value is specified no error message should be added)",
            example = "5"
    )
    @Column(name = "fee")
    private BigDecimal fee;

    @Schema(
            description = "Error message for this fee rule(if error message is specified no fee value should be added)",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Column(name = "error_message")
    private String errorMessage;

}
