package delivery.fee.calculation.controller;


import delivery.fee.calculation.dto.ExtraFee;
import delivery.fee.calculation.service.ExtraFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "REST API for managing 'Extra fee' ",
        description = "CRUD REST APIs - Create new fee, Update fee, Get All fees, Delete fee"
)
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/manage", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExtraFeeController {

    private final ExtraFeeService extraFeeService;

    @Operation(
            summary = "Get all extra fee REST API",
            description = "Get all extra fee REST API is used to get all the 'Extra fees' from the database"
    )
    @GetMapping("extrafee")
    public List<ExtraFee> getAllExtraFee() {
        return extraFeeService.getAllExtraFee();
    }


    @Operation(
            summary = "Add new extra fee REST API",
            description = "Add new extra fee REST API is used to save new 'Extra fee' in a database"
    )
    @PostMapping("extrafee")
    public String addNewExtraFee(@RequestBody ExtraFee extraFee) {
        return extraFeeService.addNewExtraFee(extraFee);
    }


    @Operation(
            summary = "Update extra fee REST API",
            description = "Update extra fee REST API is used to update existing 'Extra fee' by ID in a database"
    )
    @PutMapping("extrafee/{id}")
    public String updateExtraFee(@RequestBody ExtraFee extraFee,
                                 @PathVariable("id") Long extraFeeId) {
        return extraFeeService.updateExtraFee(extraFee, extraFeeId);
    }

    @Operation(
            summary = "Delete extra fee REST API",
            description = "Delete extra fee is used to delete existing 'Extra fee' by ID from a database"
    )
    @DeleteMapping("extrafee/{id}")
    public String deleteExtraFee(@PathVariable("id") Long extraFeeId) {
        return extraFeeService.deleteExtraFee(extraFeeId);
    }
}
