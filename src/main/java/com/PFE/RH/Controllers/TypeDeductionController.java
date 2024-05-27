package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.TypeDeductionDTO;
import com.PFE.RH.Services.TypeDeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/typedeductions")
public class TypeDeductionController {

    @Autowired
    private TypeDeductionService typeDeductionService;

    @PostMapping
    public ResponseEntity<TypeDeductionDTO> createTypeDeduction(@RequestBody TypeDeductionDTO typeDeductionDTO) {
        TypeDeductionDTO createdTypeDeduction = typeDeductionService.saveTypeDeduction(typeDeductionDTO);
        return new ResponseEntity<>(createdTypeDeduction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDeductionDTO> getTypeDeductionById(@PathVariable Long id) {
        TypeDeductionDTO typeDeduction = typeDeductionService.getTypeDeductionById(id);
        return ResponseEntity.ok(typeDeduction);
    }

    @GetMapping
    public ResponseEntity<List<TypeDeductionDTO>> getAllTypeDeductions() {
        List<TypeDeductionDTO> allTypeDeductions = typeDeductionService.getAllTypeDeductions();
        return ResponseEntity.ok(allTypeDeductions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeDeductionDTO> updateTypeDeduction(@PathVariable Long id, @RequestBody TypeDeductionDTO typeDeductionDTO) {
        TypeDeductionDTO updatedTypeDeduction = typeDeductionService.updateTypeDeduction(id, typeDeductionDTO);
        return ResponseEntity.ok(updatedTypeDeduction);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TypeDeductionDTO> patchTypeDeduction(@PathVariable Long id, @RequestBody TypeDeductionDTO typeDeductionDTO) {
        TypeDeductionDTO patchedTypeDeduction = typeDeductionService.patchTypeDeduction(id, typeDeductionDTO);
        return ResponseEntity.ok(patchedTypeDeduction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeDeduction(@PathVariable Long id) {
        boolean deleted = typeDeductionService.deleteTypeDeduction(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
