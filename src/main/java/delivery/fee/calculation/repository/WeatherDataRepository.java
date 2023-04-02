package delivery.fee.calculation.repository;

import delivery.fee.calculation.dto.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    //SELECT * FROM weather_data WHERE name="name" ORDER BY timestamp DESC LIMIT 1;
    WeatherData findFirstByNameOrderByTimestampDesc(String name);

    WeatherData findFirstByNameAndTimestampBeforeOrderByTimestampDesc(String name, Date timestamp);
}
