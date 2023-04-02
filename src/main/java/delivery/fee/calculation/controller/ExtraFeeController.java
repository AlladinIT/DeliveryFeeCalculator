package delivery.fee.calculation.controller;


import delivery.fee.calculation.dto.ExtraFee;
import delivery.fee.calculation.service.ExtraFeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/manage", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExtraFeeController {

    private final ExtraFeeService extraFeeService;

    @GetMapping("extrafee")
    public List<ExtraFee> getAllExtraFee() {
        return extraFeeService.getAllExtraFee();
    }

    @PostMapping("extrafee")
    public String addNewExtraFee(@RequestBody ExtraFee extraFee) {
        return extraFeeService.addNewExtraFee(extraFee);
    }

    @PutMapping("extrafee/{id}")
    public String updateExtraFee(@RequestBody ExtraFee extraFee,
                                 @PathVariable("id") Long extraFeeId) {
        return extraFeeService.updateExtraFee(extraFee, extraFeeId);
    }

    @DeleteMapping("extrafee/{id}")
    public String deleteExtraFee(@PathVariable("id") Long extraFeeId) {
        return extraFeeService.deleteExtraFee(extraFeeId);
    }
}
