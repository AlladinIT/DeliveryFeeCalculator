package delivery.fee.calculation.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weather_data")
public class WeatherData {
    @Id
    @SequenceGenerator(
            name = "weather_data_seq",
            sequenceName = "weather_data_seq",
            allocationSize = 1,
            initialValue = 7
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "weather_data_seq"
    )
    Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "WMOCode")
    private Integer WMOCode;

    @Column(name = "air_temperature")
    private BigDecimal airTemperature;

    @Column(name = "wind_speed")
    private BigDecimal windSpeed;

    @Column(name = "weather_phenomenon")
    private String weatherPhenomenon;

    @Column(name = "timestamp")
    private Date timestamp;

    public WeatherData(String name,
                       Integer WMOCode,
                       BigDecimal temperature,
                       BigDecimal windSpeed,
                       String weatherPhenomenon,
                       Date timestamp) {
        this.name = name;
        this.WMOCode = WMOCode;
        this.airTemperature = temperature;
        this.windSpeed = windSpeed;
        this.weatherPhenomenon = weatherPhenomenon;
        this.timestamp = timestamp;
    }
}
