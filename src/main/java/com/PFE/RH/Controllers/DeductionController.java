package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.DeductionDTO;
import com.PFE.RH.Services.DeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deductions")
public class DeductionController {

    @Autowired
    private DeductionService deductionService;

    @GetMapping
    public ResponseEntity<List<DeductionDTO>> getAllDeductions() {
        List<DeductionDTO> deductions = deductionService.getAllDeductions();
        return ResponseEntity.ok(deductions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeductionDTO> getDeductionById(@PathVariable Long id) {
        DeductionDTO deduction = deductionService.getDeductionById(id);
        return ResponseEntity.ok(deduction);
    }

    @PostMapping
    public ResponseEntity<DeductionDTO> createDeduction(@RequestBody DeductionDTO deductionDTO) {
        DeductionDTO createdDeduction = deductionService.saveDeduction(deductionDTO);
        return new ResponseEntity<>(createdDeduction, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeductionDTO> updateDeduction(@PathVariable Long id, @RequestBody DeductionDTO deductionDTO) {
        DeductionDTO updatedDeduction = deductionService.updateDeduction(id, deductionDTO);
        return ResponseEntity.ok(updatedDeduction);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DeductionDTO> patchDeduction(@PathVariable Long id, @RequestBody DeductionDTO patchedDeductionDTO) {
        DeductionDTO patchedDeduction = deductionService.patchDeduction(id, patchedDeductionDTO);
        return ResponseEntity.ok(patchedDeduction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeduction(@PathVariable Long id) {
        boolean deleted = deductionService.deleteDeduction(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
