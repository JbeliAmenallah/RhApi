package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.ContactDTO;
import com.PFE.RH.Services.ContactService;
import com.PFE.RH.Services.Keycloak.KeycloakUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Validated
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private KeycloakUserService keycloakUserService; // Inject KeycloakUserService


    @PostMapping
    public ResponseEntity<?> addContact(@Valid @RequestBody ContactDTO contactDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        // Extract entrepriseId from the contactDTO
        Long entrepriseId = contactDTO.getEntreprise().getEntrepriseId();
        contactDTO.setEntreprise(null); // Set entreprise to null to avoid serialization issues

        try {
            // Save the contact with the entrepriseId
            ContactDTO createdContact = contactService.saveContact(entrepriseId, contactDTO);

            // Create Keycloak user with contactDTO credentials
            ContactDTO createdWithKeycloakUser = keycloakUserService.createUserFromContactDTO(contactDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdWithKeycloakUser);
        } catch (NoSuchElementException e) {
            // Handle case where entreprise with the provided entrepriseId does not exist
            Map<String, String> error = new HashMap<>();
            error.put("entrepriseId", "Entreprise not found with ID: " + entrepriseId);
            return ResponseEntity.badRequest().body(error);
        }
    }


    //

    @GetMapping
    public List<ContactDTO> getAllContacts() {
        return contactService.getAllContacts();
    }

    /*  @PostMapping("/{contactId}/impots/{impotId}")
      public ResponseEntity<ContactDTO> addImpotToContact(@PathVariable Long contactId,
                                                          @PathVariable Long impotId) {
          ContactDTO updatedContact = contactService.addImpotToContact(contactId, impotId);
          return ResponseEntity.ok(updatedContact);
      }*/
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        try {
            ContactDTO contactDTO = contactService.getContactById(id);
            return ResponseEntity.ok(contactDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/employees-added-this-year")
    public ResponseEntity<Integer> getEmployeesAddedThisYear() {
        int count = contactService.getEmployeesAddedThisYear();
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactDTO updatedContactDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            ContactDTO updatedContact = contactService.updateContact(id, updatedContactDTO);
            return ResponseEntity.ok(updatedContact);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/average-base-salary")
    public ResponseEntity<Double> getAverageBaseSalary() {
        double averageBaseSalary = contactService.calculateAverageBaseSalary();
        return ResponseEntity.ok(averageBaseSalary);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContactDTO> patchContact(
            @PathVariable Long id,
            @RequestBody ContactDTO patchedContactDTO
    ) {
        try {
            ContactDTO patchedContact = contactService.patchContact(id, patchedContactDTO);
            return ResponseEntity.ok(patchedContact);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        try {
            boolean deleted = contactService.deleteContact(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
