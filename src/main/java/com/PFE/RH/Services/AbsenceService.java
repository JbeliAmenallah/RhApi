package com.PFE.RH.Services;

import com.PFE.RH.DTO.AbsenceDTO;
import com.PFE.RH.Entities.Absence;
import com.PFE.RH.Entities.Contact;
import com.PFE.RH.Mappers.AbsenceMapper;
import com.PFE.RH.Repositories.AbsenceRepository;
import com.PFE.RH.Repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private AbsenceMapper absenceMapper;
    @Autowired
    private ContactRepository contactRepository;
    public List<AbsenceDTO> getAllAbsences() {
        List<Absence> absences = absenceRepository.findAll();
        return absences.stream()
                .map(absence -> {
                    AbsenceDTO absenceDTO = absenceMapper.absenceToAbsenceDTO(absence);
                    absenceDTO.setMessage(absenceDTO.generateMessage(absence.getContact().getName()));
                    return absenceDTO;
                })
                .collect(Collectors.toList());
    }


    public AbsenceDTO saveAbsence(AbsenceDTO absenceDTO) {

        Absence absence = absenceMapper.absenceDTOToAbsence(absenceDTO);
        Absence savedAbsence = absenceRepository.save(absence);
        return absenceMapper.absenceToAbsenceDTO(savedAbsence);
    }
    public int countAbsencesByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1); // Start of the year
        LocalDate endDate = LocalDate.of(year, 12, 31); // End of the year
        List<Absence> absences = absenceRepository.findByDateDebutAbsenceBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        return absences.size();
    }
    public List<AbsenceDTO> getAbsencesByContactId(Long contactId) {
        List<Absence> absences = absenceRepository.findByContactContactId(contactId);
        return absences.stream()
                .map(absenceMapper::absenceToAbsenceDTO)
                .collect(Collectors.toList());
    }
    public List<AbsenceDTO> getAbsencesByUsername(String username){
        List<Absence> absences=absenceRepository.findByContactUsername(username);
        return absences.stream()
                .map(absenceMapper::absenceToAbsenceDTO)
                .collect(Collectors.toList());
    }

    public AbsenceDTO updateAbsence(Long id, AbsenceDTO updatedAbsenceDTO) {
        Optional<Absence> optionalAbsence = absenceRepository.findById(id);
        if (optionalAbsence.isPresent()) {
            Absence existingAbsence = optionalAbsence.get();
            existingAbsence.setDateDebutAbsence(updatedAbsenceDTO.getDateDebutAbsence());
            existingAbsence.setDateFinAbsence(updatedAbsenceDTO.getDateFinAbsence());
            existingAbsence.setReason(updatedAbsenceDTO.getReason());
            existingAbsence.setJustified(updatedAbsenceDTO.isJustified());

            // Update contact only if provided in updatedAbsenceDTO
            Long contactId = updatedAbsenceDTO.getContactId();
            if (contactId != null) {
                // Fetch the Contact entity based on contactId
                Contact contact = contactRepository.findById(contactId).orElse(null);
                existingAbsence.setContact(contact);
            }

            Absence updatedAbsence = absenceRepository.save(existingAbsence);
            return absenceMapper.absenceToAbsenceDTO(updatedAbsence);
        } else {
            updatedAbsenceDTO.setMessage("Absence not found with ID: " + id);
            return updatedAbsenceDTO;
        }
    }


    public boolean deleteAbsence(Long id) {
        Optional<Absence> optionalAbsence = absenceRepository.findById(id);
        if (optionalAbsence.isPresent()) {
            absenceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<AbsenceDTO> getAbsencesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<Absence> absences = absenceRepository.findByDateDebutAbsenceBetween(startDate, endDate);
        return absences.stream()
                .map(absenceMapper::absenceToAbsenceDTO)
                .collect(Collectors.toList());
    }


    public AbsenceDTO patchAbsence(Long id, AbsenceDTO patchedAbsenceDTO) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found with id: " + id));

        System.out.println("Patch DTO: " + patchedAbsenceDTO);

        if (patchedAbsenceDTO.getDateDebutAbsence() != null) {
            absence.setDateDebutAbsence(patchedAbsenceDTO.getDateDebutAbsence());
        }

        if (patchedAbsenceDTO.getDateFinAbsence() != null) {
            absence.setDateFinAbsence(patchedAbsenceDTO.getDateFinAbsence());
        }

        if (patchedAbsenceDTO.getReason() != null) {
            absence.setReason(patchedAbsenceDTO.getReason());
        }

        // Update contact only if provided in the DTO
        Long contactId = patchedAbsenceDTO.getContactId();
        if (contactId != null) {
            // Fetch the Contact entity based on contactId
            Contact contact = contactRepository.findById(contactId)
                    .orElseThrow(() -> new RuntimeException("Contact not found with id: " + contactId));
            absence.setContact(contact);
        }

        System.out.println("Updated Absence: " + absence);

        Absence patchedAbsence = absenceRepository.save(absence);
        return absenceMapper.absenceToAbsenceDTO(patchedAbsence);
    }





    public AbsenceDTO getAbsenceById(Long id) {
        Optional<Absence> optionalAbsence = absenceRepository.findById(id);
        return optionalAbsence.map(absenceMapper::absenceToAbsenceDTO).orElse(null);
    }
}
