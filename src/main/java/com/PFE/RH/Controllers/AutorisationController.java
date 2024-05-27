package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.AutorisationDTO;
import com.PFE.RH.Services.AutorisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/autorisations")
public class AutorisationController {

    @Autowired
    private AutorisationService autorisationService;

    @GetMapping
    public ResponseEntity<List<AutorisationDTO>> getAllAutorisations() {
        List<AutorisationDTO> autorisations = autorisationService.getAllAutorisations();
        return new ResponseEntity<>(autorisations, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveAutorisation(@Valid @RequestBody AutorisationDTO autorisationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        } else {
            AutorisationDTO savedAutorisation = autorisationService.saveAutorisation(autorisationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAutorisation);
        }
    }

    @PutMapping("/{autorisationId}")
    public ResponseEntity<?> updateAutorisation(
            @PathVariable Long autorisationId,
            @Valid @RequestBody AutorisationDTO updatedAutorisationDTO,BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        AutorisationDTO updatedAutorisation = autorisationService.updateAutorisation(autorisationId, updatedAutorisationDTO);
        return new ResponseEntity<>(updatedAutorisation, HttpStatus.OK);
    }

    @PatchMapping("/{autorisationId}")
    public ResponseEntity<AutorisationDTO> patchAutorisation(
            @PathVariable Long autorisationId,
            @RequestBody AutorisationDTO updatedAutorisationDTO
    ) {
        AutorisationDTO patchedAutorisation = autorisationService.patchAutorisation(autorisationId, updatedAutorisationDTO);
        return new ResponseEntity<>(patchedAutorisation, HttpStatus.OK);
    }

    @DeleteMapping("/{autorisationId}")
    public ResponseEntity<Void> deleteAutorisation(@PathVariable Long autorisationId) {
        autorisationService.deleteAutorisation(autorisationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<List<AutorisationDTO>> getAutorisationsByContactId(@PathVariable Long contactId) {
        List<AutorisationDTO> autorisations = autorisationService.findByContactId(contactId);
        return new ResponseEntity<>(autorisations, HttpStatus.OK);
    }

    @GetMapping("/duration-more-than-two-hours")
    public ResponseEntity<List<AutorisationDTO>> getAutorisationsWithDurationMoreThanTwoHours() {
        List<AutorisationDTO> autorisations = autorisationService.findAutorisationsWithDurationMoreThanTwoHours();
        return new ResponseEntity<>(autorisations, HttpStatus.OK);
    }

    @PostMapping("/demande")
    public ResponseEntity<AutorisationDTO> demandeAutorisation(@RequestBody AutorisationDTO autorisationDTO) {
        AutorisationDTO response = autorisationService.demandeAutorisation(autorisationDTO.getDateDebut(), autorisationDTO.getDateFin(), autorisationDTO.getContactId());
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
