package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.TypePrimeDTO;
import com.PFE.RH.Services.TypePrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/typeprimes")
public class TypePrimeController {

    @Autowired
    private TypePrimeService typePrimeService;

    @GetMapping
    public ResponseEntity<List<TypePrimeDTO>> getAllTypePrimes() {
        List<TypePrimeDTO> typePrimeDTOList = typePrimeService.getAllTypePrimes();
        return new ResponseEntity<>(typePrimeDTOList, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TypePrimeDTO> getTypePrimeById(@PathVariable Long id) {
        try {
            TypePrimeDTO typePrimeDTO = typePrimeService.getTypePrimeById(id);
            return new ResponseEntity<>(typePrimeDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<TypePrimeDTO> addTypePrime(@RequestBody TypePrimeDTO typePrimeDTO) {
        TypePrimeDTO savedTypePrime = typePrimeService.saveTypePrime(typePrimeDTO);
        return new ResponseEntity<>(savedTypePrime, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypePrimeDTO> updateTypePrime(@PathVariable Long id, @RequestBody TypePrimeDTO updatedTypePrimeDTO) {
        try {
            TypePrimeDTO updatedTypePrime = typePrimeService.updateTypePrime(id, updatedTypePrimeDTO);
            return new ResponseEntity<>(updatedTypePrime, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TypePrimeDTO> patchTypePrime(@PathVariable Long id, @RequestBody TypePrimeDTO patchedTypePrimeDTO) {
        try {
            TypePrimeDTO patchedTypePrime = typePrimeService.patchTypePrime(id, patchedTypePrimeDTO);
            return new ResponseEntity<>(patchedTypePrime, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<TypePrimeDTO> getTypePrimeByCode(@PathVariable String code) {
        try {
            TypePrimeDTO typePrimeDTO = typePrimeService.getTypePrimeByCode(code);
            return new ResponseEntity<>(typePrimeDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-libele/{libele}")
    public ResponseEntity<TypePrimeDTO> getTypePrimeByLibele(@PathVariable String libele) {
        try {
            TypePrimeDTO typePrimeDTO = typePrimeService.getTypePrimeByLibele(libele);
            return new ResponseEntity<>(typePrimeDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypePrime(@PathVariable Long id) {
        try {
            typePrimeService.deleteTypePrime(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
