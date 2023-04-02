package delivery.fee.calculation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Schema(
        description = "Input Model Information"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Input {

    @Schema(
            description = "City",
            example = "Tallinn"
    )
    @NotEmpty(message = "City should not be null or empty")
    private String city;

    @Schema(
            description = "Vehicle type",
            example = "Scooter"
    )
    @NotEmpty(message = "Vehicle type should not be null or empty")
    private String vehicleType;

    @Schema(
            description = "Date time",
            example = "2022-08-08 16:16:07",
            type = "string"
    )
    private Date dateTime;
}
