package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.FinanceConfigurationDTO;
import com.PFE.RH.Services.FinanceConfigurationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance-configurations")
public class FinanceConfigurationController {

    private final FinanceConfigurationService financeConfigurationService;

    public FinanceConfigurationController(FinanceConfigurationService financeConfigurationService) {
        this.financeConfigurationService = financeConfigurationService;
    }

    @GetMapping
    public ResponseEntity<List<FinanceConfigurationDTO>> getAllFinanceConfigurations() {
        List<FinanceConfigurationDTO> financeConfigurations = financeConfigurationService.getAllFinanceConfigurations();
        return ResponseEntity.ok(financeConfigurations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceConfigurationDTO> getFinanceConfigurationById(@PathVariable Long id) {
        FinanceConfigurationDTO financeConfiguration = financeConfigurationService.getFinanceConfigurationById(id);
        return ResponseEntity.ok(financeConfiguration);
    }
    @GetMapping("/current-or-previous")
    public ResponseEntity<FinanceConfigurationDTO> getFinanceConfigurationOfCurrentOrPreviousYear() {
        FinanceConfigurationDTO financeConfigurationDTO = financeConfigurationService.getFinanceConfigurationOfCurrentOrPreviousYear();
        return ResponseEntity.ok(financeConfigurationDTO);
    }
    @PostMapping
    public ResponseEntity<FinanceConfigurationDTO> createFinanceConfiguration(@RequestBody FinanceConfigurationDTO financeConfigurationDTO) {
        FinanceConfigurationDTO createdFinanceConfiguration = financeConfigurationService.createFinanceConfiguration(financeConfigurationDTO);
        return new ResponseEntity<>(createdFinanceConfiguration, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanceConfigurationDTO> updateFinanceConfiguration(@PathVariable Long id, @RequestBody FinanceConfigurationDTO financeConfigurationDTO) {
        FinanceConfigurationDTO updatedFinanceConfiguration = financeConfigurationService.updateFinanceConfiguration(id, financeConfigurationDTO);
        return ResponseEntity.ok(updatedFinanceConfiguration);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FinanceConfigurationDTO> patchFinanceConfiguration(@PathVariable Long id, @RequestBody FinanceConfigurationDTO financeConfigurationDTO) {
        FinanceConfigurationDTO patchedFinanceConfiguration = financeConfigurationService.patchFinanceConfiguration(id, financeConfigurationDTO);
        return ResponseEntity.ok(patchedFinanceConfiguration);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinanceConfiguration(@PathVariable Long id) {
        financeConfigurationService.deleteFinanceConfiguration(id);
        return ResponseEntity.noContent().build();
    }
}
