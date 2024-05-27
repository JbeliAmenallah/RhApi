package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.EnfantDTO;
import com.PFE.RH.Services.EnfantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enfants")
public class EnfantController {

    @Autowired
    private EnfantService enfantService;

    @GetMapping
    public ResponseEntity<List<EnfantDTO>> getAllEnfants() {
        List<EnfantDTO> enfants = enfantService.getAllEnfants();
        return new ResponseEntity<>(enfants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnfantDTO> getEnfantById(@PathVariable Long id) {
        EnfantDTO enfantDTO = enfantService.getEnfantById(id);
        return new ResponseEntity<>(enfantDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EnfantDTO> createEnfant(@RequestBody EnfantDTO enfantDTO) {
        EnfantDTO createdEnfant = enfantService.saveEnfant(enfantDTO);
        return new ResponseEntity<>(createdEnfant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnfantDTO> updateEnfant(@PathVariable Long id, @RequestBody EnfantDTO enfantDTO) {
        EnfantDTO updatedEnfant = enfantService.updateEnfant(id, enfantDTO);
        return new ResponseEntity<>(updatedEnfant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnfantById(@PathVariable Long id) {
        enfantService.deleteEnfantById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
