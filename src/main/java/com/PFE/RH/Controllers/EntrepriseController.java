package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.EntrepriseDTO;
import com.PFE.RH.DTO.EntrepriseWithoutContactsDTO;
import com.PFE.RH.Services.EntrepriseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/entreprise")
public class EntrepriseController {

    private final EntrepriseService entrepriseService;

    @Autowired
    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @GetMapping
    public ResponseEntity<List<EntrepriseDTO>> getAllEntreprises() {
        List<EntrepriseDTO> entreprises = entrepriseService.getAllEntreprises();
        return new ResponseEntity<>(entreprises, HttpStatus.OK);
    }

    @RequestMapping(value="/add",method=RequestMethod.POST)
    public ResponseEntity<?> createEntreprise(@Valid @RequestBody EntrepriseDTO entrepriseDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        } else {
            EntrepriseDTO createdEntreprise = entrepriseService.saveEntreprise(entrepriseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntreprise);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntrepriseDTO> updateEntreprise(@PathVariable Long id, @RequestBody EntrepriseDTO updatedEntrepriseDTO) {
        EntrepriseDTO updatedEntreprise = entrepriseService.updateEntreprise(id, updatedEntrepriseDTO);
        return new ResponseEntity<>(updatedEntreprise, HttpStatus.OK);
    }

    @PostMapping("/{entrepriseId}/addGrade/{gradeId}")
    public ResponseEntity<EntrepriseWithoutContactsDTO> addGradeToEntreprise(@PathVariable Long entrepriseId, @PathVariable Long gradeId) {
        EntrepriseWithoutContactsDTO updatedEntreprise = entrepriseService.addGradeToEntreprise(entrepriseId, gradeId);
        return new ResponseEntity<>(updatedEntreprise, HttpStatus.OK);
    }

    @PostMapping("/{entrepriseId}/addGroupe/{groupeId}")
    public ResponseEntity<EntrepriseWithoutContactsDTO> addGroupeToEntreprise(@PathVariable Long entrepriseId, @PathVariable Long groupeId) {
        EntrepriseWithoutContactsDTO updatedEntreprise = entrepriseService.addGroupeToEntreprise(entrepriseId, groupeId);
        return new ResponseEntity<>(updatedEntreprise, HttpStatus.OK);
    }

    @PostMapping("/{entrepriseId}/addCategory/{categoryId}")
    public ResponseEntity<EntrepriseWithoutContactsDTO> addCategoryToEntreprise(@PathVariable Long entrepriseId, @PathVariable Long categoryId) {
        EntrepriseWithoutContactsDTO updatedEntreprise = entrepriseService.addCategoryToEntreprise(entrepriseId, categoryId);
        return new ResponseEntity<>(updatedEntreprise, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntreprise(@PathVariable Long id) {
         entrepriseService.deleteEntreprise(id);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EntrepriseDTO> getEntrepriseById(@PathVariable Long id) {
        EntrepriseDTO entreprise = entrepriseService.getEntrepriseById(id);
        return new ResponseEntity<>(entreprise, HttpStatus.OK);
    }
}
