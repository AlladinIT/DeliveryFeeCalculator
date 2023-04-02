package delivery.fee.calculation.dto;


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

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "extra_fee_type")
    private String extraFeeType;

    @Column(name = "first_parameter")
    private String firstParameter;

    @Column(name = "second_parameter")
    private String secondParameter;

    @Column(name = "fee")
    private BigDecimal fee;

    @Column(name = "error_message")
    private String errorMessage;

}
