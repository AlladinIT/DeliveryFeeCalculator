package delivery.fee.calculation.repository;

import delivery.fee.calculation.dto.RegionalBaseFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionalBaseFeeRepository extends JpaRepository<RegionalBaseFee, Long> {
    RegionalBaseFee findByCityIgnoreCase(String city);
}
