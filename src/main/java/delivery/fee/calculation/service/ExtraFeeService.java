package delivery.fee.calculation.service;

import delivery.fee.calculation.dto.ExtraFee;
import delivery.fee.calculation.repository.ExtraFeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ExtraFeeService {

    private final ExtraFeeRepository extraFeeRepository;

    public List<ExtraFee> getAllExtraFee() {
        try {
            return extraFeeRepository.findAll();
        } catch (Exception e) {
            System.out.println("Error while listing extra fees: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public String addNewExtraFee(ExtraFee extraFee) {
        try {
            if (!userInputIsValid(extraFee)) {
                return "Input is invalid \n" +
                        "You should include 'extraFeeType' (ATEF or WSEF or WPEF), 'vehicleType', either 'fee' or 'errorMessage'," +
                        " and at least one parameter: 'firstParameter' or 'secondParameter' to add new extra fee \n" +
                        "(in case of feeType = 'WPEF' only firstParameter should be included)";
            }

            List<ExtraFee> listOfRules = extraFeeRepository.findAllByExtraFeeTypeIgnoreCaseAndVehicleTypeIgnoreCase(
                    extraFee.getExtraFeeType(),
                    extraFee.getVehicleType()
            );

            boolean newRuleIsValid = true;
            for (ExtraFee existingRule : listOfRules) {
                if (newRuleContradictsWithExistingRule(extraFee, existingRule)) {
                    newRuleIsValid = false;
                    break;
                }
            }
            if (!newRuleIsValid) {
                return "Error! Your new rule contradicts with the existing ones!";
            }


            extraFeeRepository.save(extraFee);
            return "New extra fee was added successfully";
        } catch (Exception e) {
            return "Error while saving new extra fee: " + e.getMessage();
        }
    }

    public String updateExtraFee(ExtraFee updatedExtraFee, Long extraFeeId) {
        try {
            if (!userInputIsValid(updatedExtraFee)) {
                return "Input is invalid \n" +
                        "You should include 'extraFeeType' (ATEF or WSEF or WPEF), 'vehicleType', either 'fee' or 'errorMessage'," +
                        " and at least one parameter: 'firstParameter' or 'secondParameter' to add new extra fee \n" +
                        "(in case of feeType = 'WPEF' only firstParameter should be included)";
            }

            List<ExtraFee> listOfRules = extraFeeRepository.findAllByExtraFeeTypeIgnoreCaseAndVehicleTypeIgnoreCase(
                    updatedExtraFee.getExtraFeeType(),
                    updatedExtraFee.getVehicleType()
            );

            ExtraFee savedExtraFee = extraFeeRepository.findById(extraFeeId).orElseThrow(
                    () -> new EntityNotFoundException("You don't have extra fee with id: " + extraFeeId + " saved in database.")
            );

            boolean newRuleIsValid = true;
            for (ExtraFee existingRule : listOfRules) {
                if (newRuleContradictsWithExistingRule(updatedExtraFee, existingRule) && !existingRule.getId().equals(savedExtraFee.getId())) {
                    newRuleIsValid = false;
                    break;
                }
            }
            if (!newRuleIsValid) {
                return "Error! Your new rule contradicts with the existing ones!";
            }

            savedExtraFee.setExtraFeeType(updatedExtraFee.getExtraFeeType());
            savedExtraFee.setFee(updatedExtraFee.getFee());
            savedExtraFee.setVehicleType(updatedExtraFee.getVehicleType());
            savedExtraFee.setFirstParameter(updatedExtraFee.getFirstParameter());
            savedExtraFee.setSecondParameter(updatedExtraFee.getSecondParameter());
            savedExtraFee.setErrorMessage(updatedExtraFee.getErrorMessage());

            extraFeeRepository.save(savedExtraFee);
            return "Extra fee with ID: " + extraFeeId + " was changed successfully";
        } catch (Exception e) {
            return "Error while updating extra fee: " + e.getMessage();
        }
    }

    public String deleteExtraFee(Long extraFeeId) {
        try {
            extraFeeRepository.findById(extraFeeId).orElseThrow(
                    () -> new EntityNotFoundException("You don't have extra fee with id: " + extraFeeId + " saved in database.")
            );

            extraFeeRepository.deleteById(extraFeeId);
            return "Extra fee with ID: " + extraFeeId + " was deleted successfully";
        } catch (Exception e) {
            return "Error while deleting RBF: " + e.getMessage();
        }
    }

    private boolean userInputIsValid(ExtraFee extraFee) {
        if (
                extraFee.getId() != null ||
                        extraFee.getExtraFeeType() == null ||
                        extraFee.getVehicleType() == null ||
                        extraFee.getFee() == null && extraFee.getErrorMessage() == null ||
                        extraFee.getFee() != null && extraFee.getErrorMessage() != null ||
                        extraFee.getFirstParameter() == null && extraFee.getSecondParameter() == null ||
                        extraFee.getExtraFeeType().equalsIgnoreCase("WPEF") && extraFee.getFirstParameter() == null ||
                        !extraFee.getExtraFeeType().equalsIgnoreCase("WPEF") &&
                                !extraFee.getExtraFeeType().equalsIgnoreCase("ATEF") &&
                                !extraFee.getExtraFeeType().equalsIgnoreCase("WSEF") ||
                        !extraFee.getExtraFeeType().equalsIgnoreCase("WPEF") &&
                                new BigDecimal(extraFee.getFirstParameter()).compareTo(new BigDecimal(extraFee.getSecondParameter())) > 0

        ) {
            return false;
        }

        return true;
    }

    private boolean newRuleContradictsWithExistingRule(ExtraFee extraFee, ExtraFee existingRule) {

        if (extraFee.getExtraFeeType().equalsIgnoreCase("WPEF")) {
            return extraFee.getFirstParameter().equalsIgnoreCase(existingRule.getFirstParameter());
        } else {
            BigDecimal newLeft;
            BigDecimal newRight;

            if (extraFee.getFirstParameter() == null) {
                newLeft = new BigDecimal(Integer.MIN_VALUE);
            } else {
                newLeft = new BigDecimal(extraFee.getFirstParameter());
            }

            if (extraFee.getFirstParameter() == null) {
                newRight = new BigDecimal(Integer.MAX_VALUE);
            } else {
                newRight = new BigDecimal(extraFee.getSecondParameter());
            }


            BigDecimal existingLeft;
            BigDecimal existingRight;

            if (existingRule.getFirstParameter() == null) {
                existingLeft = new BigDecimal(Integer.MIN_VALUE);
            } else {
                existingLeft = new BigDecimal(existingRule.getFirstParameter());
            }

            if (existingRule.getSecondParameter() == null) {
                existingRight = new BigDecimal(Integer.MAX_VALUE);
            } else {
                existingRight = new BigDecimal(existingRule.getSecondParameter());
            }

            return !(existingLeft.compareTo(newRight) > 0 || existingRight.compareTo(newLeft) < 0) ||
                    existingLeft.compareTo(newLeft) == 0 && existingRight.compareTo(newRight) == 0;
        }
    }
}
