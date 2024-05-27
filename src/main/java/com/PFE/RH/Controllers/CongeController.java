package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.CongeDTO;
import com.PFE.RH.Services.CongeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/conges")
public class CongeController {

    @Autowired
    private CongeService congeService;

    @PostMapping
    public ResponseEntity<?> addConge(@Valid  @RequestBody CongeDTO congeDTO , BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        CongeDTO savedConge = congeService.saveConge(congeDTO);
        return new ResponseEntity<>(savedConge, HttpStatus.CREATED);
    }
    @GetMapping("/per-month")
    public ResponseEntity<List<Map<String, Object>>> getCongesPerMonth() {
        List<Map<String, Object>> congésPerMonth = congeService.getCongesPerMonth();
        return new ResponseEntity<>(congésPerMonth, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CongeDTO>> getAllConges() {
        List<CongeDTO> congeDTOList = congeService.getAllConges();
        return new ResponseEntity<>(congeDTOList, HttpStatus.OK);
    }
    @GetMapping("/pending/count")
    public ResponseEntity<Integer> getPendingCongeCount() {
        int pendingCongeCount = congeService.countPendingConges();
        return new ResponseEntity<>(pendingCongeCount, HttpStatus.OK);
    }
    @GetMapping("/{congeId}")
    public ResponseEntity<CongeDTO> getConge(@PathVariable Long congeId) {
        CongeDTO congeDTO = congeService.getCongeById(congeId);
        if (congeDTO != null) {
            return new ResponseEntity<>(congeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{congeId}")
    public ResponseEntity<?> updateConge(  @PathVariable Long congeId, @Valid @RequestBody CongeDTO updatedCongeDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        CongeDTO updatedConge = congeService.updateConge(congeId, updatedCongeDTO);
        if (updatedConge != null) {
            return new ResponseEntity<>(updatedConge, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{congeId}")
    public ResponseEntity<Void> deleteConge(@PathVariable Long congeId) {
        boolean deleted = congeService.deleteConge(congeId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-contact/{contactId}")
    public ResponseEntity<List<CongeDTO>> getCongesByContactId(@PathVariable Long contactId) {
        List<CongeDTO> congesByContact = congeService.getCongesByContactId(contactId);
        if (!congesByContact.isEmpty()) {
            return new ResponseEntity<>(congesByContact, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{congeId}")
    public ResponseEntity<CongeDTO> patchConge(@PathVariable Long congeId, @RequestBody CongeDTO patchedCongeDTO) {
        CongeDTO patchedConge = congeService.patchConge(congeId, patchedCongeDTO);
        if (patchedConge != null) {
            return new ResponseEntity<>(patchedConge, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<CongeDTO>> getCongesByUsername(@PathVariable String username) {
        List<CongeDTO> congeDTOList = congeService.getCongesByUsername(username);
        return new ResponseEntity<>(congeDTOList, HttpStatus.OK);
    }

    @PostMapping("/demande")
    public ResponseEntity<CongeDTO> demandeConge(@RequestBody CongeDTO congeDTO) {
        LocalDate startDate = congeDTO.getStartDate();
        LocalDate endDate = congeDTO.getEndDate();
        Long contactId = congeDTO.getContactId();
         congeDTO = congeService.demandeConge(startDate, endDate, contactId);
        if (congeDTO != null) {
            return new ResponseEntity<>(congeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
