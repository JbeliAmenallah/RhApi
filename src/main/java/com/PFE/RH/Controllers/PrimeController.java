package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.PrimeDTO;
import com.PFE.RH.Services.PrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/primes")
public class PrimeController {

    @Autowired
    private PrimeService primeService;

    @PostMapping
    public ResponseEntity<PrimeDTO> addPrime(@RequestBody PrimeDTO primeDTO) {
        PrimeDTO savedPrime = primeService.savePrime(primeDTO);
        return new ResponseEntity<>(savedPrime, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PrimeDTO>> getAllPrimes() {
        List<PrimeDTO> primeDTOList = primeService.getAllPrimes();
        return new ResponseEntity<>(primeDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrimeDTO> getPrimeById(@PathVariable Long id) {
        PrimeDTO primeDTO = primeService.getPrimeById(id);
        if (primeDTO != null) {
            return new ResponseEntity<>(primeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrimeDTO> updatePrime(@PathVariable Long id, @RequestBody PrimeDTO updatedPrimeDTO) {
        PrimeDTO updatedPrime = primeService.patchPrime(id, updatedPrimeDTO);
        if (updatedPrime != null) {
            return new ResponseEntity<>(updatedPrime, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/contact-ids/{primeId}")
    public ResponseEntity<List<Long>> getContactIdsByPrimeId(@PathVariable Long primeId) {
        List<Long> contactIds = primeService.getContactIdsByPrimeId(primeId);
        if (!contactIds.isEmpty()) {
            return new ResponseEntity<>(contactIds, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addByCategoryGradeOrGroup")
    public List<PrimeDTO> addPrimeToEmployeesByCategoryGradeOrGroup(
            @RequestBody PrimeDTO primeDTO) {
        return primeService.addPrimeToEmployeesByCategoryGradeOrGroup(primeDTO);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<PrimeDTO> patchPrime(@PathVariable Long id, @RequestBody PrimeDTO patchedPrimeDTO) {
        try {
            PrimeDTO patchedPrime = primeService.patchPrime(id, patchedPrimeDTO);
            if (patchedPrime != null) {
                return new ResponseEntity<>(patchedPrime, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrime(@PathVariable Long id) {
        boolean deleted = primeService.deletePrime(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Continued from previous response...

    @GetMapping("/by-contact/{contactId}")
    public ResponseEntity<List<PrimeDTO>> getPrimesByContactId(@PathVariable Long contactId) {
        List<PrimeDTO> primesByContact = primeService.getPrimesByContactId(contactId);
        if (!primesByContact.isEmpty()) {
            return new ResponseEntity<>(primesByContact, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
