package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.FicheDePaieDTO;
import com.PFE.RH.Services.FicheDePaieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fichesdepaie")
public class FicheDePaieController {

    private final FicheDePaieService ficheDePaieService;

    @Autowired
    public FicheDePaieController(FicheDePaieService ficheDePaieService) {
        this.ficheDePaieService = ficheDePaieService;
    }

    @GetMapping
    public ResponseEntity<List<FicheDePaieDTO>> getAllFichesDePaie() {
        List<FicheDePaieDTO> fichesDePaie = ficheDePaieService.getAllFichesDePaie();
        return new ResponseEntity<>(fichesDePaie, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FicheDePaieDTO> getFicheDePaieById(@PathVariable Long id) {
        FicheDePaieDTO ficheDePaie = ficheDePaieService.getFicheDePaieById(id);
        return new ResponseEntity<>(ficheDePaie, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FicheDePaieDTO> createFicheDePaie(@RequestBody FicheDePaieDTO ficheDePaieDTO) {
        FicheDePaieDTO createdFicheDePaie = ficheDePaieService.saveFicheDePaie(ficheDePaieDTO);
        return new ResponseEntity<>(createdFicheDePaie, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFicheDePaie(@PathVariable Long id) {
        ficheDePaieService.deleteFicheDePaie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<List<FicheDePaieDTO>> getFichesDePaieByContactId(@PathVariable String contactId) {
        // Parse the contactId string to Long
        Long parsedContactId = Long.parseLong(contactId);
        List<FicheDePaieDTO> fichesDePaie = ficheDePaieService.getFicheDePaieByContactId(parsedContactId);
        return new ResponseEntity<>(fichesDePaie, HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<FicheDePaieDTO> updatePartialFicheDePaie(@PathVariable Long id, @RequestBody FicheDePaieDTO updatedFicheDePaieDTO) {
        FicheDePaieDTO updatedFicheDePaie = ficheDePaieService.updatePartialFicheDePaie(id, updatedFicheDePaieDTO);
        return new ResponseEntity<>(updatedFicheDePaie, HttpStatus.OK);
    }

}
