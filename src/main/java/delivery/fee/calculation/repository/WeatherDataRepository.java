package delivery.fee.calculation.repository;

import delivery.fee.calculation.dto.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    /**
     * This method finds the latest weather conditions for a chosen City (name)(case-insensitive) by timestamp in a database.
     * @param name City where you want to know latest weather conditions
     * @return Weather data class or null if not found
     */
    WeatherData findFirstByNameIgnoreCaseOrderByTimestampDesc(String name);

    /**
     * This method finds valid weather conditions for a chosen city (name)(case-insensitive) and for a chosen date (timestamp) in a database.
     * @param name City where you want to know weather conditions.
     * @param timestamp Chosen date (finds the first one before this date)
     * @return Weather data class or null if not found
     */
    WeatherData findFirstByNameIgnoreCaseAndTimestampBeforeOrderByTimestampDesc(String name, Date timestamp);
}
