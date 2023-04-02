package delivery.fee.calculation.repository;

import delivery.fee.calculation.dto.ExtraFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraFeeRepository extends JpaRepository<ExtraFee, Long> {
    List<ExtraFee> findAllByExtraFeeTypeIgnoreCaseAndVehicleTypeIgnoreCase(String extraFeeType, String vehicleType);
}