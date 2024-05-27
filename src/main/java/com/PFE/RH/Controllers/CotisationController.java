package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.CotisationDTO;
import com.PFE.RH.Services.CotisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cotisations")
public class CotisationController {

    @Autowired
    private CotisationService cotisationService;

    @PostMapping
    public ResponseEntity<CotisationDTO> addCotisation(@RequestBody CotisationDTO cotisationDTO) {
        CotisationDTO savedCotisation = cotisationService.saveCotisation(cotisationDTO);
        if (savedCotisation != null) {
            return new ResponseEntity<>(savedCotisation, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CotisationDTO> patchCotisation(@PathVariable("id") Long cotisationId,
                                                         @RequestBody CotisationDTO patchedCotisationDTO) {
        CotisationDTO updatedCotisation = cotisationService.patchCotisation(cotisationId, patchedCotisationDTO);
        if (updatedCotisation != null) {
            return ResponseEntity.ok(updatedCotisation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CotisationDTO> getCotisationById(@PathVariable("id") Long cotisationId) {
        CotisationDTO cotisationDTO = cotisationService.getCotisationById(cotisationId);
        if (cotisationDTO != null) {
            return new ResponseEntity<>(cotisationDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<CotisationDTO>> getAllCotisations() {
        List<CotisationDTO> cotisations = cotisationService.getAllCotisations();
        return new ResponseEntity<>(cotisations, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CotisationDTO> updateCotisation(
            @PathVariable("id") Long cotisationId,
            @RequestBody CotisationDTO updatedCotisationDTO
    ) {
        CotisationDTO updatedCotisation = cotisationService.updateCotisation(cotisationId, updatedCotisationDTO);
        if (updatedCotisation != null) {
            return new ResponseEntity<>(updatedCotisation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   /* @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCotisation(@PathVariable("id") Long cotisationId) {
        boolean deleted = cotisationService.deleteCotisation(cotisationId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
}
