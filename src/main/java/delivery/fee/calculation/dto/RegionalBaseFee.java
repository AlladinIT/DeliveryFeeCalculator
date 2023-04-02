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
@Table(name = "regional_base_fee")
public class RegionalBaseFee {
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

    @Column(name = "city")
    private String city;

    @Column(name = "carRBF")
    private BigDecimal carRBF;

    @Column(name = "scooterRBF")
    private BigDecimal scooterRBF;

    @Column(name = "bikeRBF")
    private BigDecimal bikeRBF;

}
