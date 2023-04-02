package delivery.fee.calculation.repository;

import delivery.fee.calculation.dto.ExtraFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraFeeRepository extends JpaRepository<ExtraFee, Long> {
    /**
     * This method finds all Extra fee rules by fee type (case-insensitive) and by vehicle type(case-insensitive) in a database.
     * @param extraFeeType fee type (ATEF, WSEF or WPEF)
     * @param vehicleType vehicle type (car, scooter, bike, etc)
     * @return List of Extra fee classes or null if not found.
     */
    List<ExtraFee> findAllByExtraFeeTypeIgnoreCaseAndVehicleTypeIgnoreCase(String extraFeeType, String vehicleType);
}