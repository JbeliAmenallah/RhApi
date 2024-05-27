package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.AbsenceDTO;
import com.PFE.RH.Services.AbsenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/absences")
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    @GetMapping
    public ResponseEntity<List<AbsenceDTO>> getAllAbsences() {
        List<AbsenceDTO> absences = absenceService.getAllAbsences();
        return new ResponseEntity<>(absences, HttpStatus.OK);
    }
    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<AbsenceDTO>> getAllAbsencesByUsername(@PathVariable String username) {
        List<AbsenceDTO> absences = absenceService.getAbsencesByUsername(username);
        return new ResponseEntity<>(absences, HttpStatus.OK);
    }
    @GetMapping("/count-by-year")
    public ResponseEntity<Integer> countAbsencesByYear(@RequestParam("year") int year) {
        int absenceCount = absenceService.countAbsencesByYear(year);
        return new ResponseEntity<>(absenceCount, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AbsenceDTO> getAbsenceById(@PathVariable Long id) {
        AbsenceDTO absence = absenceService.getAbsenceById(id);
        if (absence != null) {
            return new ResponseEntity<>(absence, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateAbsence(@PathVariable Long id, @RequestBody @Valid AbsenceDTO updatedAbsenceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        AbsenceDTO updatedAbsence = absenceService.updateAbsence(id, updatedAbsenceDTO);
        if (updatedAbsence != null) {
            return new ResponseEntity<>(updatedAbsence, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addAbsence(@Valid @RequestBody AbsenceDTO absenceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        // Call the service method to add the absence
        AbsenceDTO addedAbsence = absenceService.saveAbsence(absenceDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedAbsence);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        boolean deleted = absenceService.deleteAbsence(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-contact/{contactId}")
    public ResponseEntity<List<AbsenceDTO>> getAbsencesByContactId(@PathVariable Long contactId) {
        List<AbsenceDTO> absencesByContact = absenceService.getAbsencesByContactId(contactId);
        if (!absencesByContact.isEmpty()) {
            return new ResponseEntity<>(absencesByContact, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/between-dates")
    public ResponseEntity<List<AbsenceDTO>> getAbsencesBetweenDates(@RequestParam("startDate") LocalDateTime startDate,
                                                                    @RequestParam("endDate") LocalDateTime endDate) {
        List<AbsenceDTO> absences = absenceService.getAbsencesBetweenDates(startDate, endDate);
        if (!absences.isEmpty()) {
            return new ResponseEntity<>(absences, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
