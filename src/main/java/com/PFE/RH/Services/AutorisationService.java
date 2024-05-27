package com.PFE.RH.Services;

import com.PFE.RH.DTO.AutorisationDTO;
import com.PFE.RH.Entities.Autorisation;
import com.PFE.RH.Entities.Contact;
import com.PFE.RH.Mappers.AutorisationMapper;
import com.PFE.RH.Repositories.AutorisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.PFE.RH.Repositories.ContactRepository;



import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorisationService {
    @Autowired
    private  AutorisationRepository autorisationRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private AutorisationMapper autorisationMapper;

    public List<AutorisationDTO> getAllAutorisations() {
        List<Autorisation> autorisations = autorisationRepository.findAll();
        return autorisations.stream()
                .map(autorisationMapper::autorisationToAutorisationDTO)
                .collect(Collectors.toList());
    }

    public AutorisationDTO saveAutorisation(@Valid @NotNull AutorisationDTO autorisationDTO) {
        try {
            Autorisation autorisation = autorisationMapper.autorisationDTOToAutorisation(autorisationDTO);
            Autorisation savedAutorisation = autorisationRepository.save(autorisation);
            return autorisationMapper.autorisationToAutorisationDTO(savedAutorisation);
        } catch (ConstraintViolationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public AutorisationDTO updateAutorisation(Long autorisationId, @Valid @NotNull AutorisationDTO updatedAutorisationDTO) {
        Autorisation autorisation = autorisationRepository.findById(autorisationId)
                .orElseThrow(() -> new RuntimeException("Autorisation not found with id: " + autorisationId));
        autorisation.setDateDebut(updatedAutorisationDTO.getDateDebut());
        autorisation.setDateFin(updatedAutorisationDTO.getDateFin());
        autorisation.setState(updatedAutorisationDTO.getState());

        // Update contact if provided in updated DTO
        if (updatedAutorisationDTO.getContactId() != null) {
            Contact updatedContact = contactRepository.findById(updatedAutorisationDTO.getContactId())
                    .orElseThrow(() -> new RuntimeException("Contact not found with id: " + updatedAutorisationDTO.getContactId()));
            autorisation.setContact(updatedContact);
        }

        Autorisation updatedAutorisation = autorisationRepository.save(autorisation);
        return autorisationMapper.autorisationToAutorisationDTO(updatedAutorisation);
    }



    public void deleteAutorisation(Long autorisationId) {
        autorisationRepository.deleteById(autorisationId);
    }

    public AutorisationDTO patchAutorisation(Long autorisationId, AutorisationDTO updatedAutorisationDTO) {
        Autorisation autorisation = autorisationRepository.findById(autorisationId)
                .orElseThrow(() -> new RuntimeException("Autorisation not found with id: " + autorisationId));

        if (updatedAutorisationDTO.getDateDebut() != null) {
            autorisation.setDateDebut(updatedAutorisationDTO.getDateDebut());
        }

        if (updatedAutorisationDTO.getDateFin() != null) {
            autorisation.setDateFin(updatedAutorisationDTO.getDateFin());
        }

        if (updatedAutorisationDTO.getState() != null) {
            autorisation.setState(updatedAutorisationDTO.getState());
        }

        Autorisation patchedAutorisation = autorisationRepository.save(autorisation);
        return autorisationMapper.autorisationToAutorisationDTO(patchedAutorisation);
    }

    public List<AutorisationDTO> findByContactId(Long contactId) {
        List<Autorisation> autorisations = autorisationRepository.findByContact_ContactId(contactId);
        return autorisations.stream()
                .map(autorisationMapper::autorisationToAutorisationDTO)
                .collect(Collectors.toList());
    }

    public List<AutorisationDTO> findAutorisationsWithDurationMoreThanTwoHours() {
        List<Autorisation> autorisations = autorisationRepository.findAll();
        return autorisations.stream()
                .filter(this::hasDurationMoreThanTwoHours)
                .map(autorisationMapper::autorisationToAutorisationDTO)
                .collect(Collectors.toList());
    }

    private boolean hasDurationMoreThanTwoHours(Autorisation autorisation) {
        LocalDateTime dateDebut = autorisation.getDateDebut();
        LocalDateTime dateFin = autorisation.getDateFin();
        if (dateDebut != null && dateFin != null) {
            Duration duration = Duration.between(dateDebut, dateFin);
            return duration.toHours() > 2;
        }
        return false;
    }


    public AutorisationDTO demandeAutorisation(LocalDateTime startDate, LocalDateTime endDate, Long contactId) {
        contactId = 3L; // Hardcoded contactId value
        Optional<Contact> contactOptional = contactRepository.findById(contactId);
        if (contactOptional.isPresent()) {
            Contact contact = contactOptional.get();
            // Add null checks for startDate and endDate
            if (startDate != null && endDate != null) {
                // Calculate duration between startDate and endDate
                Duration duration = Duration.between(startDate, endDate);
                // Initialize state
                String state;
                if (duration.toHours() <= 2) {
                    state = "Accepted";
                } else {
                    state = "Rejected";
                }
                // Create and save the autorisation
                Autorisation autorisation = new Autorisation();
                autorisation.setDateDebut(startDate);
                autorisation.setDateFin(endDate);
                autorisation.setContact(contact);
                autorisation.setState(state);
                autorisationRepository.save(autorisation);
                // Return autorisationDTO
                return autorisationMapper.autorisationToAutorisationDTO(autorisation);
            } else {
                // Handle case where startDate or endDate is null
                // You can throw an exception, return null, or handle it according to your requirements
            }
        }
        return null;
    }


}
