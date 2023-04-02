package delivery.fee.calculation.service;

import delivery.fee.calculation.dto.*;
import delivery.fee.calculation.repository.RegionalBaseFeeRepository;
import delivery.fee.calculation.repository.WeatherDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryFeeCalculatorService {

    private static final String TALLINN = "TALLINN";
    private static final String TARTU = "TARTU";
    private static final String CAR = "CAR";
    private static final String SCOOTER = "SCOOTER";
    private static final String BIKE = "BIKE";
    private final WeatherDataRepository weatherDataRepository;
    private final RegionalBaseFeeRepository regionalBaseFeeRepository;
    private final delivery.fee.calculation.repository.ExtraFeeRepository extraFeeRepository;

    /**
     * This method calculates the delivery fee for food couriers based on regional base fee, vehicle type, and weather
     * conditions. All those factors are stored inside a database.
     * @param input Input class containing City(required), Vehicle type(required) and Date time(not required).
     * @return Output class containing either delivery fee or an error.
     */
    public Output calculate(Input input) {
        try {
            //===============================INPUT VALIDATION =============================
            if (input == null || input.getCity() == null || input.getVehicleType() == null) {
                return new Output(
                        null,
                        "Input is null!"
                );
            }

            String city = input.getCity();
            String vehicleType = input.getVehicleType();

            if (!vehicleType.equalsIgnoreCase(CAR) && !vehicleType.equalsIgnoreCase(SCOOTER) && !vehicleType.equalsIgnoreCase(BIKE)) {
                return new Output(
                        null,
                        "You can choose only one vehicle type: Car, Scooter or Bike."
                );
            }
            //=========================================================================================

            //==============================CALCULATE REGIONAL BASE FEE============================
            BigDecimal regionalBaseFee = calculateRegionalBaseFee(city, vehicleType);

            if (regionalBaseFee == null) {
                return new Output(
                        null,
                        "You can't deliver food to the chosen city"
                );
            }
            //====================================================================================


            //=====================EXTRACTING CURRENT WEATHER FROM OUR DATABASE=========================
            String arg = city;
            if (city.equalsIgnoreCase(TALLINN)) {
                arg = "Tallinn-Harku";
            }
            if (city.equalsIgnoreCase(TARTU)) {
                arg = "Tartu-Tõravere";
            }

            WeatherData weather;
            if (input.getDateTime() != null) {
                weather = weatherDataRepository.findFirstByNameAndTimestampBeforeOrderByTimestampDesc(arg, input.getDateTime());
                if (weather == null) {
                    return new Output(
                            null,
                            "There is no data regarding weather conditions for this period of time"
                    );
                }
            } else {
                weather = weatherDataRepository.findFirstByNameOrderByTimestampDesc(arg);
                if (weather == null) {
                    return new Output(
                            null,
                            "The weather data was not yet inserted into database by Cron job"
                    );
                }
            }
            //==========================================================================================


            //======================CALCULATE EXTRA FEES FOR WEATHER CONDITION============================
            Output extraFeeForWeatherCondition = calculateExtraFeeForWeatherCondition(weather, vehicleType);
            if (extraFeeForWeatherCondition.getErrorMessage() != null) {
                return extraFeeForWeatherCondition; //HAS ERROR MESSAGE
            }
            //============================================================================================
            return new Output(
                    regionalBaseFee.add(extraFeeForWeatherCondition.getDeliveryFee()),
                    null
            );
        } catch (Exception e) {
            return new Output(
                    null,
                    "Error in delivery fee calculation: " + e.getMessage()
            );
        }
    }

    private Output calculateExtraFeeForWeatherCondition(WeatherData weather, String vehicleType) {
        BigDecimal extraFee = new BigDecimal(0);

        BigDecimal value = new BigDecimal(0); //EITHER AIR TEMPERATURE IN °C OR WIND SPEED IN m/s
        String weatherPhenomenon = weather.getWeatherPhenomenon();

        List<ExtraFee> extraFeeConditionList = extraFeeRepository.findAll();
        for (ExtraFee extraFeeCondition : extraFeeConditionList) {
            if (extraFeeCondition.getExtraFeeType().equalsIgnoreCase("ATEF")) {
                value = weather.getAirTemperature();
            } else if (extraFeeCondition.getExtraFeeType().equalsIgnoreCase("WSEF")) {
                value = weather.getWindSpeed();
            }

            //=================Extra fee based on air temperature (ATEF) or wind speed (WSEF)=======================
            if (extraFeeCondition.getExtraFeeType().equalsIgnoreCase("ATEF") ||
                    extraFeeCondition.getExtraFeeType().equalsIgnoreCase("WSEF")) {
                if (extraFeeCondition.getVehicleType().equalsIgnoreCase(vehicleType) &&
                        ((extraFeeCondition.getFirstParameter() == null
                                || new BigDecimal(extraFeeCondition.getFirstParameter()).compareTo(value) <= 0) &&
                                (extraFeeCondition.getSecondParameter() == null
                                        || new BigDecimal(extraFeeCondition.getSecondParameter()).compareTo(value) >= 0))) {
                    if (extraFeeCondition.getErrorMessage() != null) {
                        return new Output(
                                null,
                                extraFeeCondition.getErrorMessage()
                        );
                    } else {
                        extraFee = extraFee.add(extraFeeCondition.getFee());
                    }

                }
            }
            //==================================================================================================

            //========================Extra fee based on weather phenomenon (WPEF) ==============================
            else if (extraFeeCondition.getExtraFeeType().equalsIgnoreCase("WPEF") &&
                    extraFeeCondition.getVehicleType().equalsIgnoreCase(vehicleType) &&
                    weatherPhenomenon.toUpperCase().contains(extraFeeCondition.getFirstParameter().toUpperCase())) {
                if (extraFeeCondition.getErrorMessage() != null) {
                    return new Output(
                            null,
                            extraFeeCondition.getErrorMessage()
                    );
                } else {
                    extraFee = extraFee.add(extraFeeCondition.getFee());
                }
            }
            //===================================================================================================
        }

        return new Output(
                extraFee,
                null
        );
    }

    private BigDecimal calculateRegionalBaseFee(String city, String vehicleType) {
        RegionalBaseFee rbfValues = regionalBaseFeeRepository.findByCityIgnoreCase(city);

        if (rbfValues == null) {
            return null;
        }

        if (vehicleType.equalsIgnoreCase(CAR)) {
            return rbfValues.getCarRBF();
        }
        if (vehicleType.equalsIgnoreCase(SCOOTER)) {
            return rbfValues.getScooterRBF();
        }
        return rbfValues.getBikeRBF();
    }
}
