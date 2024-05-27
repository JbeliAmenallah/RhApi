package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.JourFerieDTO;
import com.PFE.RH.Services.JourFerieService;
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
@RequestMapping("/api/jourferies")
public class JourFerieController {

    @Autowired
    private JourFerieService jourFerieService;

    @GetMapping
    public ResponseEntity<List<JourFerieDTO>> getAllJourFeries() {
        List<JourFerieDTO> allJourFeries = jourFerieService.getAllJourFeries();
        return ResponseEntity.ok(allJourFeries);
    }
    @PostMapping
    public ResponseEntity<?> createJourFerie(@Valid @RequestBody JourFerieDTO jourFerieDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        } else {
            JourFerieDTO createdJourFerie = jourFerieService.saveJourFerie(jourFerieDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdJourFerie);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JourFerieDTO> updateJourFerie(@PathVariable Long id, @RequestBody JourFerieDTO jourFerieDTO) {
        JourFerieDTO updatedJourFerie = jourFerieService.updateJourFerie(id, jourFerieDTO);
        return ResponseEntity.ok(updatedJourFerie);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JourFerieDTO> partialUpdateJourFerie(@PathVariable Long id, @RequestBody JourFerieDTO partialJourFerieDTO) {
        JourFerieDTO updatedJourFerie = jourFerieService.partialUpdateJourFerie(id, partialJourFerieDTO);
        return ResponseEntity.ok(updatedJourFerie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJourFerie(@PathVariable Long id) {
        boolean deleted = jourFerieService.deleteJourFerie(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
