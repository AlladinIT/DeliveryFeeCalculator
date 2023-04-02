package delivery.fee.calculation.controller;


import delivery.fee.calculation.dto.RegionalBaseFee;
import delivery.fee.calculation.service.RegionalBaseFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "REST API for managing 'Regional base fee' ",
        description = "CRUD REST APIs - Create new fee, Update fee, Get All fees, Delete fee"
)
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/manage", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegionalBaseFeeController {

    private final RegionalBaseFeeService regionalBaseFeeService;


    /**
     * This method lists all Regional base fee rules that are saved in a database.
     * @return List of RegionalBaseFee classes containing fee rules.
     */
    @Operation(
            summary = "Get all Regional base fee REST API",
            description = "Get all Regional base fee REST API is used to get all the 'Regional base fees' from the database"
    )
    @GetMapping("basefee")
    public List<RegionalBaseFee> getAllRegionalBaseFee() {
        return regionalBaseFeeService.getAllRegionalBaseFee();
    }



    /**
     * This method saves new Regional base fee rule in a database.
     * @param newRegionalBaseFee new Regional base fee rule.
     * @return String response(Either was successful or an error).
     */
    @Operation(
            summary = "Add new Regional base fee REST API",
            description = "Add new Regional base fee REST API is used to save new 'Regional base fee' in a database"
    )
    @PostMapping("basefee")
    public String addNewRegionalBaseFee(@RequestBody RegionalBaseFee newRegionalBaseFee) {
        return regionalBaseFeeService.addNewRegionalBaseFee(newRegionalBaseFee);
    }




    /**
     * This method updates Regional fee rule in a database.
     * @param updatedRegionalBaseFee updated Regional fee rule
     * @param regionalBaseFeeId ID of Regional fee rule you want to update
     * @return String response(Either was successful or an error).
     */
    @Operation(
            summary = "Update Regional base fee REST API",
            description = "Update Regional base fee REST API is used to update existing 'Regional base fee' by ID in a database"
    )
    @PutMapping("basefee/{id}")
    public String updateRegionalBaseFee(@RequestBody RegionalBaseFee updatedRegionalBaseFee,
                                        @PathVariable("id") Long regionalBaseFeeId) {
        return regionalBaseFeeService.updateRegionalBaseFee(updatedRegionalBaseFee, regionalBaseFeeId);
    }



    /**
     * This method deletes Regional fee rule from a database.
     * @param regionalBaseFeeId ID of the Regional fee rule to delete
     * @return String response(Either was successful or an error).
     */
    @Operation(
            summary = "Delete Regional base fee REST API",
            description = "Delete Regional base fee is used to delete existing 'Regional base fee' by ID from a database"
    )
    @DeleteMapping("basefee/{id}")
    public String deleteRegionalBaseFee(@PathVariable("id") Long regionalBaseFeeId) {
        return regionalBaseFeeService.deleteRegionalBaseFee(regionalBaseFeeId);
    }
}
