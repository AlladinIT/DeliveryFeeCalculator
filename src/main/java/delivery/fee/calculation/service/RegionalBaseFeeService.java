package delivery.fee.calculation.service;

import delivery.fee.calculation.dto.RegionalBaseFee;
import delivery.fee.calculation.repository.RegionalBaseFeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor
public class RegionalBaseFeeService {

    private final RegionalBaseFeeRepository regionalBaseFeeRepository;


    /**
     * This method lists all Regional base fee rules that are saved in a database.
     * @return List of RegionalBaseFee classes containing fee rules.
     */
    public List<RegionalBaseFee> getAllRegionalBaseFee() {
        try {
            return regionalBaseFeeRepository.findAll();
        } catch (Exception e) {
            System.out.println("Error while listing RBF: " + e.getMessage());
            return Collections.emptyList();
        }

    }


    /**
     * This method saves new Regional base fee rule in a database.
     * @param newRegionalBaseFee new Regional base fee rule.
     * @return String response(Either was successful or an error).
     */
    public String addNewRegionalBaseFee(RegionalBaseFee newRegionalBaseFee) {
        try {
            if (!userInputIsValid(newRegionalBaseFee)) {
                return "You should include 4 key-value pairs (city, carRBF, scooterRBF, bikeRBF) to add new RBF.";
            }

            RegionalBaseFee test = regionalBaseFeeRepository.findByCityIgnoreCase(newRegionalBaseFee.getCity());
            if (test != null) {
                return "You already have regional base fee for this city.";
            }

            regionalBaseFeeRepository.save(newRegionalBaseFee);
            return "New regional base fee was added successfully";
        } catch (Exception e) {
            return "Error while saving new RBF: " + e.getMessage();
        }
    }


    /**
     * This method updates Regional fee rule in a database.
     * @param updatedRegionalBaseFee updated Regional fee rule
     * @param regionalBaseFeeId ID of Regional fee rule you want to update
     * @return String response(Either was successful or an error).
     */
    public String updateRegionalBaseFee(RegionalBaseFee updatedRegionalBaseFee, Long regionalBaseFeeId) {
        try {
            if (!userInputIsValid(updatedRegionalBaseFee)) {
                return "You should include 4 key-value pairs (city, carRBF, scooterRBF, bikeRBF) to add new RBF.";
            }

            RegionalBaseFee savedRBF = regionalBaseFeeRepository.findById(regionalBaseFeeId).orElseThrow(
                    () -> new EntityNotFoundException("You don't have RBF with id: " + regionalBaseFeeId + " saved in database.")
            );

            RegionalBaseFee savedRBFWithSameName = regionalBaseFeeRepository
                    .findByCityIgnoreCase(updatedRegionalBaseFee.getCity());

            if (savedRBFWithSameName != null && !savedRBF.getCity().equalsIgnoreCase(savedRBFWithSameName.getCity())) {
                return "You already have regional base fee for this city.";
            }

            savedRBF.setCity(updatedRegionalBaseFee.getCity());
            savedRBF.setBikeRBF(updatedRegionalBaseFee.getBikeRBF());
            savedRBF.setCarRBF(updatedRegionalBaseFee.getCarRBF());
            savedRBF.setScooterRBF(updatedRegionalBaseFee.getScooterRBF());

            regionalBaseFeeRepository.save(savedRBF);

            return "You have successfully updated RBF with id: " + regionalBaseFeeId;
        } catch (Exception e) {
            return "Error while updating RBF: " + e.getMessage();
        }
    }


    /**
     * This method deletes Regional fee rule from a database.
     * @param regionalBaseFeeId ID of the Regional fee rule to delete
     * @return String response(Either was successful or an error).
     */
    public String deleteRegionalBaseFee(Long regionalBaseFeeId) {
        try {
            regionalBaseFeeRepository.findById(regionalBaseFeeId).orElseThrow(
                    () -> new EntityNotFoundException("You don't have RBF with id: " + regionalBaseFeeId + " saved in database.")
            );

            regionalBaseFeeRepository.deleteById(regionalBaseFeeId);
            return "RBF with ID: " + regionalBaseFeeId + " was deleted successfully";
        } catch (Exception e) {
            return "Error while deleting RBF: " + e.getMessage();
        }
    }


    private boolean userInputIsValid(RegionalBaseFee regionalBaseFee) {
        if (
                regionalBaseFee.getBikeRBF() == null ||
                        regionalBaseFee.getCarRBF() == null ||
                        regionalBaseFee.getScooterRBF() == null ||
                        regionalBaseFee.getCity() == null ||
                        regionalBaseFee.getId() != null) {
            return false;
        }

        return true;
    }


}
