package com.PFE.RH.Services;

import com.PFE.RH.DTO.CongeDTO;
import com.PFE.RH.Entities.Conge;
import com.PFE.RH.Entities.Contact;
import com.PFE.RH.Mappers.CongeMapper;
import com.PFE.RH.Repositories.CongeRepository;
import com.PFE.RH.Repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CongeService {

    @Autowired
    private CongeRepository congeRepository;

    @Autowired
    private CongeMapper congeMapper;

    @Autowired
    private ContactRepository contactRepository;

  /*  public CongeDTO saveConge(CongeDTO congeDTO) {
        Optional<Contact> contactOptional = contactRepository.findById(congeDTO.getContactId());
        if (contactOptional.isPresent()) {
            Contact contact = contactOptional.get();
            Conge conge = congeMapper.congeDTOToConge(congeDTO);
            conge.setContact(contact);
            // Check if endDate - startDate >= soldeConge of Contact
            if (ChronoUnit.DAYS.between(conge.getStartDate(), conge.getEndDate()) >= contact.getSoldeConge()) {
                conge.setState("Accepted");
            } else {
                conge.setState("Pending");
            }
            contact.getConges().add(conge);
            return congeMapper.congeToCongeDTO(congeRepository.save(conge));
        }
        return null;
    }*/
  public CongeDTO saveConge(CongeDTO congeDTO) {
      return contactRepository.findById(congeDTO.getContactId())
              .map(contact -> {
                  Conge conge = congeMapper.congeDTOToConge(congeDTO);
                  conge.setContact(contact);
                  contact.getConges().add(conge);
                  return congeMapper.congeToCongeDTO(congeRepository.save(conge));
              })
              .orElse(null);
  }

    public List<Map<String, Object>> getCongesPerMonth() {
        // Get the current year and previous year
        int currentYear = LocalDate.now().getYear();
        int previousYear = currentYear - 1;

        // Fetch all congés for the current year and previous year
        List<Conge> congés = congeRepository.findAllByYear(currentYear);
        congés.addAll(congeRepository.findAllByYear(previousYear));

        // Group congés by month and year
        Map<Month, Long> congesPerMonthCurrentYear = new HashMap<>();
        Map<Month, Long> congesPerMonthPreviousYear = new HashMap<>();
        for (Conge conge : congés) {
            LocalDate startDate = conge.getStartDate();
            Month month = startDate.getMonth();
            int year = startDate.getYear();
            if (year == currentYear) {
                congesPerMonthCurrentYear.put(month, congesPerMonthCurrentYear.getOrDefault(month, 0L) + 1);
            } else if (year == previousYear) {
                congesPerMonthPreviousYear.put(month, congesPerMonthPreviousYear.getOrDefault(month, 0L) + 1);
            }
        }

        // Convert to a list of maps with month, year, and congesCount for current year
        List<Map<String, Object>> result = congesPerMonthCurrentYear.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("year", currentYear);
                    map.put("month", entry.getKey().toString());
                    map.put("congesCount", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());

        // Append congesCount for previous year
        result.addAll(congesPerMonthPreviousYear.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("year", previousYear);
                    map.put("month", entry.getKey().toString());
                    map.put("congesCount", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList()));

        return result;
    }
    public int countPendingConges() {
        List<Conge> pendingConges = congeRepository.findAll().stream()
                .filter(conge -> "En attente".equals(conge.getState()))
                .collect(Collectors.toList());
        return pendingConges.size();
    }
    public CongeDTO getCongeById(Long congeId) {
        Optional<Conge> congeOptional = congeRepository.findById(congeId);
        return congeOptional.map(congeMapper::congeToCongeDTO).orElse(null);
    }

    public CongeDTO updateConge(Long congeId, CongeDTO updatedCongeDTO) {
        Optional<Conge> congeOptional = congeRepository.findById(congeId);
        if (congeOptional.isPresent()) {
            Conge conge = congeOptional.get();
            conge.setStartDate(updatedCongeDTO.getStartDate());
            conge.setEndDate(updatedCongeDTO.getEndDate());
            conge.setState(updatedCongeDTO.getState());
            Optional<Contact> contactOptional = contactRepository.findById(updatedCongeDTO.getContactId());
            if (contactOptional.isPresent()) {
                Contact contact = contactOptional.get();
                conge.setContact(contact);
                contactRepository.save(contact);
            }
            congeRepository.save(conge);
            return congeMapper.congeToCongeDTO(conge);
        }
        return null;
    }

    public List<CongeDTO> getAllConges() {
        List<Conge> conges = congeRepository.findAll();
        return conges.stream()
                .map(congeMapper::congeToCongeDTO)
                .collect(Collectors.toList());
    }

    public List<CongeDTO> getCongesByUsername(String username) {
        List<Conge> conges=congeRepository.findByContactUsername(username);
        return conges.stream()
                .map(congeMapper::congeToCongeDTO)
                .collect(Collectors.toList());
    }

    public boolean deleteConge(Long congeId) {
        Optional<Conge> congeOptional = congeRepository.findById(congeId);
        if (congeOptional.isPresent()) {
            Conge conge = congeOptional.get();
            conge.getContact().getConges().remove(conge);
            congeRepository.deleteById(congeId);
            return true;
        }
        return false;
    }

    public List<CongeDTO> getCongesByContactId(Long contactId) {
        List<Conge> conges = congeRepository.findByContactContactId(contactId);
        return conges.stream()
                .map(congeMapper::congeToCongeDTO)
                .collect(Collectors.toList());
    }

    public CongeDTO patchConge(Long congeId, CongeDTO patchedCongeDTO) {
        Optional<Conge> congeOptional = congeRepository.findById(congeId);
        if (congeOptional.isPresent()) {
            Conge conge = congeOptional.get();

            if (patchedCongeDTO.getStartDate() != null) {
                conge.setStartDate(patchedCongeDTO.getStartDate());
            }
            if (patchedCongeDTO.getEndDate() != null) {
                conge.setEndDate(patchedCongeDTO.getEndDate());
            }
            if (patchedCongeDTO.getState() != null) {
                conge.setState(patchedCongeDTO.getState());
            }

            congeRepository.save(conge);
            return congeMapper.congeToCongeDTO(conge);
        }
        return null;
    }
    public CongeDTO demandeConge(LocalDate startDate, LocalDate endDate,Long contactId ) {
         contactId = 7L; // Hardcoded contactId value
        Optional<Contact> contactOptional = contactRepository.findById(contactId);
        if (contactOptional.isPresent()) {
            Contact contact = contactOptional.get();
            int soldeConge = contact.getSoldeConge();
            int requestedDays = (int) ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)); // Including end date
            if (soldeConge >= requestedDays) {
                // Create and save the congé with accepted state
                Conge conge = new Conge();
                conge.setStartDate(startDate);
                conge.setEndDate(endDate);
                conge.setContact(contact);
                conge.setState("Accepted");
                contact.getConges().add(conge);
                congeRepository.save(conge);
                // Update soldeConge for the contact
                contact.setSoldeConge(soldeConge - requestedDays);
                contactRepository.save(contact);
                return congeMapper.congeToCongeDTO(conge);
            } else {
                // Create and save the congé with pending state
                Conge conge = new Conge();
                conge.setStartDate(startDate);
                conge.setEndDate(endDate);
                conge.setContact(contact);
                conge.setState("En Attente");
                contact.getConges().add(conge);
                congeRepository.save(conge);
                return congeMapper.congeToCongeDTO(conge);
            }
        }
        return null;
    }

}

    // Method to update soldeConge for existing contacts
   /* public void updateSoldeCongeForExistingContacts() {
        List<Contact> contacts = contactRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        for (Contact contact : contacts) {
            LocalDate recruitmentDate = contact.getDateRecrutemnt();
            long monthsOfWork = ChronoUnit.MONTHS.between(recruitmentDate, currentDate);
            if (monthsOfWork > 0) {
                int daysToAdd = (int) (monthsOfWork * 2); // Assuming 2 days added per month
                contact.setSoldeConge(daysToAdd);
                contactRepository.save(contact);
            }
        }
    }

    // Scheduled task to update soldeConge every month
    @Scheduled(cron = "0 0 0/24 * * *") // Run every 24 hours
    public void updateSoldeCongeEvery24Hours() {
        List<Contact> contacts = contactRepository.findAll();
        for (Contact contact : contacts) {
            int soldeConge = contact.getSoldeConge();
            soldeConge += 2; // Assuming 2 days added per month
            contact.setSoldeConge(soldeConge);
            contactRepository.save(contact);
        }
    }*/

