package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.AnneeDTO;
import com.PFE.RH.DTO.AnneeWithoutJourFerieDTO;
import com.PFE.RH.Services.AnneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annees")
public class AnneeController {

    @Autowired
    private AnneeService anneeService;

    @PostMapping
    public ResponseEntity<AnneeWithoutJourFerieDTO> createAnnee(@RequestBody AnneeDTO anneeDTO) {
        AnneeWithoutJourFerieDTO createdAnnee = anneeService.saveAnnee(anneeDTO);
        return new ResponseEntity<>(createdAnnee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnneeWithoutJourFerieDTO> getAnneeById(@PathVariable Long id) {
        AnneeWithoutJourFerieDTO annee = anneeService.getAnneeById(id);
        return ResponseEntity.ok(annee);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<AnneeWithoutJourFerieDTO> patchAnnee(@PathVariable Long id, @RequestBody AnneeDTO anneeDTO) {
        AnneeWithoutJourFerieDTO patchedAnnee = anneeService.patchAnnee(id, anneeDTO);
        return ResponseEntity.ok(patchedAnnee);
    }

    @GetMapping
    public ResponseEntity<List<AnneeWithoutJourFerieDTO>> getAllAnnees() {
        List<AnneeWithoutJourFerieDTO> allAnnees = anneeService.getAllAnnees();
        return ResponseEntity.ok(allAnnees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnneeWithoutJourFerieDTO> updateAnnee(@PathVariable Long id, @RequestBody AnneeDTO anneeDTO) {
        AnneeWithoutJourFerieDTO updatedAnnee = anneeService.updateAnnee(id, anneeDTO);
        return ResponseEntity.ok(updatedAnnee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnee(@PathVariable Long id) {
        boolean deleted = anneeService.deleteAnnee(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
