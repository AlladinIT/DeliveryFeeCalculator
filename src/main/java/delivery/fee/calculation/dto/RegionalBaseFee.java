package delivery.fee.calculation.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "regional_base_fee")
public class RegionalBaseFee {
    @Schema(
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Id
    @SequenceGenerator(
            name = "regional_base_fee_seq",
            sequenceName = "regional_base_fee_seq",
            allocationSize = 1,
            initialValue = 4
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "regional_base_fee_seq"
    )
    Long id;


    @Schema(
            description = "City where the fees are assigned for different vehicles",
            example = "Narva"
    )
    @Column(name = "city")
    private String city;


    @Schema(
            description = "Regional base fee for a CAR vehicle type",
            example = "4"
    )
    @Column(name = "carRBF")
    private BigDecimal carRBF;

    @Schema(
            description = "Regional base fee for a SCOOTER vehicle type",
            example = "3.5"
    )
    @Column(name = "scooterRBF")
    private BigDecimal scooterRBF;


    @Schema(
            description = "Regional base fee for a BIKE vehicle type",
            example = "2"
    )
    @Column(name = "bikeRBF")
    private BigDecimal bikeRBF;

}
