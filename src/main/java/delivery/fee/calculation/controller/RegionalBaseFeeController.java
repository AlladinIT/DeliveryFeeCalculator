package delivery.fee.calculation.controller;


import delivery.fee.calculation.dto.RegionalBaseFee;
import delivery.fee.calculation.service.RegionalBaseFeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/manage", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegionalBaseFeeController {

    private final RegionalBaseFeeService regionalBaseFeeService;

    @GetMapping("basefee")
    public List<RegionalBaseFee> getAllRegionalBaseFee() {
        return regionalBaseFeeService.getAllRegionalBaseFee();
    }

    @PostMapping("basefee")
    public String addNewRegionalBaseFee(@RequestBody RegionalBaseFee newRegionalBaseFee) {
        return regionalBaseFeeService.addNewRegionalBaseFee(newRegionalBaseFee);
    }

    @PutMapping("basefee/{id}")
    public String updateRegionalBaseFee(@RequestBody RegionalBaseFee updatedRegionalBaseFee,
                                        @PathVariable("id") Long regionalBaseFeeId) {
        return regionalBaseFeeService.updateRegionalBaseFee(updatedRegionalBaseFee, regionalBaseFeeId);
    }

    @DeleteMapping("basefee/{id}")
    public String deleteRegionalBaseFee(@PathVariable("id") Long regionalBaseFeeId) {
        return regionalBaseFeeService.deleteRegionalBaseFee(regionalBaseFeeId);
    }
}
