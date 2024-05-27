package com.PFE.RH.Services;

import com.PFE.RH.DTO.ContactDTO;
import com.PFE.RH.Entities.*;
import com.PFE.RH.Mappers.ContactMapper;
import com.PFE.RH.Mappers.ImpotProjectionMapper;
import com.PFE.RH.Repositories.ContactRepository;
import com.PFE.RH.Repositories.EntrepriseRepository;
import com.PFE.RH.Repositories.ImpotRepository;
import com.PFE.RH.Mappers.CategoryMapper;
import com.PFE.RH.Repositories.CategoryRepository;
import com.PFE.RH.Mappers.GradeMapper;
import com.PFE.RH.Repositories.GradeRepository;
import com.PFE.RH.Mappers.GroupeMapper;
import com.PFE.RH.Repositories.GroupeRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final EntrepriseRepository entrepriseRepository;
    private final ImpotRepository impotRepository;
    private final ImpotProjectionMapper impotProjectionMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    private final GradeMapper gradeMapper;

    private final GradeRepository gradeRepository;

    private final GroupeMapper groupeMapper;
    private final GroupeRepository groupeRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository,ImpotProjectionMapper impotProjectionMapper ,ContactMapper contactMapper,ImpotRepository impotRepository, EntrepriseRepository entrepriseRepository, CategoryRepository categoryRepository,CategoryMapper categoryMapper,GradeRepository gradeRepository,GradeMapper gradeMapper,GroupeRepository groupeRepository,GroupeMapper groupeMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
        this.entrepriseRepository = entrepriseRepository;
        this.impotRepository=impotRepository;
        this.impotProjectionMapper=impotProjectionMapper;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
        this.groupeRepository = groupeRepository;
        this.groupeMapper = groupeMapper;

    }

    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .map(contactMapper::contactToContactDTO)
                .collect(Collectors.toList());
    }
  /*  public ContactDTO getContactById(Long id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            // Calculate soldeConge based on logic
            LocalDate currentDate = LocalDate.now();
            LocalDate recrutementDate = contact.getDateRecrutemnt();
            long months = ChronoUnit.MONTHS.between(recrutementDate, currentDate);
            int soldeConge = (int) (months * 2); // Add 2 days of soldeConge for each month

             //Ensure soldeConge is at least 0
             soldeConge = Math.max(soldeConge, 0);

           //  Set the calculated soldeConge to the contact
             contact.setSoldeConge(soldeConge);

            // Save the updated contact with the new soldeConge value
            Contact updatedContact = contactRepository.save(contact);
            return contactMapper.contactToContactDTO(updatedContact);
        } else {
            throw new NoSuchElementException("Contact not found with ID: " + id);
        }
    }*/
  public ContactDTO getContactById(Long id) {
      Optional<Contact> optionalContact = contactRepository.findById(id);
      if (optionalContact.isPresent()) {
          Contact contact = optionalContact.get();
          return contactMapper.contactToContactDTO(contact);
      } else {
          throw new NoSuchElementException("Contact not found with ID: " + id);
      }
  }
    public ContactDTO saveContact(Long entrepriseId, @Valid ContactDTO contactDTO) {
        Contact contact = contactMapper.contactDTOToContact(contactDTO);

        // Get the specific entreprise by ID
        Optional<Entreprise> optionalEntreprise = entrepriseRepository.findById(entrepriseId);

        if (optionalEntreprise.isPresent()) {
            Entreprise entreprise = optionalEntreprise.get();
            contact.setEntreprise(entreprise); // Set the contact's entreprise

            // Set the category if provided
            if (contactDTO.getCategory() != null) {
                System.out.println("grade error1");

                Optional<Category> optionalCategory = categoryRepository.findById(contactDTO.getCategory().getCategory_id());
                optionalCategory.ifPresent(contact::setCategory);
            }
            if (contactDTO.getGrade() != null) {
                System.out.println("grade error2");
                Optional<Grade> optionalGrade = gradeRepository.findById(contactDTO.getGrade().getGrade_id());
                optionalGrade.ifPresent(contact::setGrade);
            }
            if (contactDTO.getGroupe() != null) {
                System.out.println("grade error3");

                Optional<Groupe> optionalGroupe = groupeRepository.findById(contactDTO.getGroupe().getGroupe_id());
                optionalGroupe.ifPresent(contact::setGroupe);
            }

            Contact savedContact = contactRepository.save(contact);
            return contactMapper.contactToContactDTO(savedContact);
        } else {
            // Handle the case where entreprise with the given ID does not exist
            throw new NoSuchElementException("Entreprise with ID " + entrepriseId + " not found");
        }
    }

    public double calculateAverageBaseSalary() {
        // Get all contacts/employees
        List<Contact> contacts = contactRepository.findAll();

        // Calculate the total base salary
        double totalBaseSalary = contacts.stream()
                .mapToDouble(Contact::getSalaireDeBASE) // Extract base salary
                .sum();

        // Calculate the average base salary
        double averageBaseSalary = totalBaseSalary / contacts.size();

        return averageBaseSalary;
    }

    public int getEmployeesAddedThisYear() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get all contacts/employees
        List<Contact> contacts = contactRepository.findAll();

        // Filter contacts/employees added in the current year
        List<Contact> employeesAddedThisYear = contacts.stream()
                .filter(contact -> contact.getDateRecrutemnt().getYear() == currentDate.getYear())
                .collect(Collectors.toList());

        // Return the count of employees added in the current year
        return employeesAddedThisYear.size();
    }

    public ContactDTO updateContact(Long id, ContactDTO updatedContactDTO) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact existingContact = optionalContact.get();
            existingContact.setName(updatedContactDTO.getName());
            existingContact.setUsername(updatedContactDTO.getUsername());
            existingContact.setEmail(updatedContactDTO.getEmail());
            existingContact.setLocation(updatedContactDTO.getLocation());
            existingContact.setPhone(updatedContactDTO.getPhone());

            // Update category if provided, or set to null if not provided
            if (updatedContactDTO.getCategory() != null && updatedContactDTO.getCategory().getCategory_id() != null) {
                // If category is provided in the DTO, retrieve it and set it to the contact
                Optional<Category> optionalCategory = categoryRepository.findById(updatedContactDTO.getCategory().getCategory_id());
                System.out.println(optionalCategory);
                optionalCategory.ifPresent(existingContact::setCategory);
            } else {
                // If category is not provided, set it to null in the contact
                existingContact.setCategory(null);
            }
            if (updatedContactDTO.getGrade() != null && updatedContactDTO.getGrade().getGrade_id() != null) {
                Optional<Grade> optionalGrade = gradeRepository.findById(updatedContactDTO.getGrade().getGrade_id());
                optionalGrade.ifPresent(existingContact::setGrade);
            } else {
                existingContact.setGrade(null);
            }
            if (updatedContactDTO.getGroupe() != null && updatedContactDTO.getGroupe().getGroupe_id() != null) {
                Optional<Groupe> optionalGroupe = groupeRepository.findById(updatedContactDTO.getGroupe().getGroupe_id());
                optionalGroupe.ifPresent(existingContact::setGroupe);
            } else {
                existingContact.setGroupe(null);
            }
            if (updatedContactDTO.getEntreprise() != null && updatedContactDTO.getEntreprise().getEntrepriseId() != null) {
                Optional<Entreprise> optionalEntreprise = entrepriseRepository.findById(updatedContactDTO.getEntreprise().getEntrepriseId());
                optionalEntreprise.ifPresent(existingContact::setEntreprise);
            }


            Contact updatedContact = contactRepository.save(existingContact);
            return contactMapper.contactToContactDTO(updatedContact);
        } else {
            throw new NoSuchElementException("Contact not found with ID: " + id);
        }
    }


    // PATCH method to update specific fields
    public ContactDTO patchContact(Long id,ContactDTO patchedContactDTO) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();

            if (patchedContactDTO.getName() != null) {
                contact.setName(patchedContactDTO.getName());
            }
            if (patchedContactDTO.getUsername() != null) {
                contact.setUsername(patchedContactDTO.getUsername());
            }
            if (patchedContactDTO.getEmail() != null) {
                contact.setEmail(patchedContactDTO.getEmail());
            }
            if (patchedContactDTO.getLocation() != null) {
                contact.setLocation(patchedContactDTO.getLocation());
            }
            if (patchedContactDTO.getPhone() != null) {
                contact.setPhone(patchedContactDTO.getPhone());
            }
            if (patchedContactDTO.getFax() != null) {
                contact.setFax(patchedContactDTO.getFax());
            }
            if (patchedContactDTO.getPassword() != null) {
                contact.setPassword(patchedContactDTO.getPassword());
            }
            if (patchedContactDTO.getRoles() != null) {
                contact.setRoles(patchedContactDTO.getRoles());
            }
            if (patchedContactDTO.getNbEnfant() != null) {
                contact.setNbEnfant(patchedContactDTO.getNbEnfant());
            }
            if (patchedContactDTO.getRegime() != null) {
                contact.setRegime(patchedContactDTO.getRegime());
            }

                contact.setChefDefamille(patchedContactDTO.isChefDefamille());

            if (patchedContactDTO.getSalaireDeBASE() != null) {
                contact.setSalaireDeBASE(patchedContactDTO.getSalaireDeBASE());
            }
            if (patchedContactDTO.getNumCompte() != null) {
                contact.setNumCompte(patchedContactDTO.getNumCompte());
            }
            if (patchedContactDTO.getModeDePaiement() != null) {
                contact.setModeDePaiement(patchedContactDTO.getModeDePaiement());
            }
            if (patchedContactDTO.getDateRecrutemnt() != null) {
                contact.setDateRecrutemnt(patchedContactDTO.getDateRecrutemnt());
            }
            if (patchedContactDTO.getSoldeConge()!=null) {
                contact.setSoldeConge(patchedContactDTO.getSoldeConge());
            }
            // Update category if provided, or set to null if not provided
            if (patchedContactDTO.getCategory() != null && patchedContactDTO.getCategory().getCategory_id() != null) {
                // If category is provided in the DTO, retrieve it and set it to the contact
                Optional<Category> optionalCategory = categoryRepository.findById(patchedContactDTO.getCategory().getCategory_id());
                System.out.println(optionalCategory);
                optionalCategory.ifPresent(contact::setCategory);
            } else {
                // If category is not provided, set it to null in the contact
                contact.setCategory(null);
            }
            if (patchedContactDTO.getGrade() != null && patchedContactDTO.getGrade().getGrade_id() != null) {
                Optional<Grade> optionalGrade = gradeRepository.findById(patchedContactDTO.getGrade().getGrade_id());
                optionalGrade.ifPresent(contact::setGrade);
            } else {
                contact.setGrade(null);
            }
            if (patchedContactDTO.getGroupe() != null && patchedContactDTO.getGroupe().getGroupe_id() != null) {
                Optional<Groupe> optionalGroupe = groupeRepository.findById(patchedContactDTO.getGroupe().getGroupe_id());
                optionalGroupe.ifPresent(contact::setGroupe);
            } else {
                contact.setGroupe(null);
            }
            if (patchedContactDTO.getEntreprise() != null && patchedContactDTO.getEntreprise().getEntrepriseId() != null) {
                Optional<Entreprise> optionalEntreprise = entrepriseRepository.findById(patchedContactDTO.getEntreprise().getEntrepriseId());
                optionalEntreprise.ifPresent(contact::setEntreprise);
            }

            Contact patchedContact = contactRepository.save(contact);
            return contactMapper.contactToContactDTO(patchedContact);
        } else {
            throw new NoSuchElementException("Contact not found with ID: " + id);
        }
    }
 /*   public ContactDTO addImpotToContact(Long contactId, Long impotId) {
        Optional<Contact> optionalContact = contactRepository.findById(contactId);
        Optional<Impot> optionalImpot = impotRepository.findById(impotId);

        if (optionalContact.isPresent() && optionalImpot.isPresent()) {
            Contact contact = optionalContact.get();
            Impot impot = optionalImpot.get();

            ImpotProjectionDTO impotDTO = impotProjectionMapper.toDto(impot);
            contact.addImpot(impotDTO); // Add the impot to the contact's impots list
            contactRepository.save(contact);

            return contactMapper.contactToContactDTO(contact);
        } else {
            throw new NoSuchElementException("Contact or Impot not found");
        }
    }*/
   /* public ContactDTO getContactById(Long id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            return contactMapper.contactToContactDTO(contact);
        } else {
            throw new NoSuchElementException("Contact not found with ID: " + id);
        }
    }*/

    public boolean deleteContact(Long id) {
        try {
            contactRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("Contact not found with ID: " + id);
        }
    }

    public Map<String, DoubleSummaryStatistics> calculateSalaryStatisticsByGrade() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .collect(Collectors.groupingBy(
                        contact -> contact.getGrade().getLibele(), // Assuming grade has a name or identifier
                        Collectors.summarizingDouble(Contact::getSalaireDeBASE)
                ));
    }

    public Map<String, DoubleSummaryStatistics> calculateSalaryStatisticsByGroup() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .collect(Collectors.groupingBy(
                        contact -> contact.getGroupe().getLibele(), // Assuming groupe has a name or identifier
                        Collectors.summarizingDouble(Contact::getSalaireDeBASE)
                ));
    }

    public double calculateAverageTenureInYears() {
        LocalDate currentDate = LocalDate.now();
        List<Contact> contacts = contactRepository.findAll();
        double totalTenureInMonths = contacts.stream()
                .mapToLong(contact -> ChronoUnit.MONTHS.between(contact.getDateRecrutemnt(), currentDate))
                .sum();
        return totalTenureInMonths / (contacts.size() * 12); // Convert months to years
    }

    public int getTotalAbsenceDays(Long contactId) {
        Optional<Contact> optionalContact = contactRepository.findById(contactId);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            List<Absence> absences = contact.getAbsences(); // Get the list of absences for the contact
            int totalAbsenceDays = 0;
            for (Absence absence : absences) {
                // Calculate the number of days for each absence and add it to the total
                totalAbsenceDays += calculateNumberOfDays(absence.getDateDebutAbsence(), absence.getDateFinAbsence());
            }
            return totalAbsenceDays;
        } else {
            throw new NoSuchElementException("Contact not found with ID: " + contactId);
        }
    }

    // Helper method to calculate the number of days between two dates
    private int calculateNumberOfDays(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();
        return (int) ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)); // Adding 1 to include the end date
    }






}
