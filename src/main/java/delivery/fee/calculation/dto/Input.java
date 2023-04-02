package delivery.fee.calculation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Input {
    private String city;
    private String vehicleType;
    private Date dateTime;
}
