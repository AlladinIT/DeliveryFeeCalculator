package delivery.fee.calculation.repository;

import delivery.fee.calculation.dto.RegionalBaseFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionalBaseFeeRepository extends JpaRepository<RegionalBaseFee, Long> {
    /**
     * This method finds Regional base fee by City (case-insensitive) in a database.
     * @param city
     * @return Regional base fee class or null if not found.
     */
    RegionalBaseFee findByCityIgnoreCase(String city);
}
