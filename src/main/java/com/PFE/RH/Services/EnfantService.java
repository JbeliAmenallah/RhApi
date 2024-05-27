package com.PFE.RH.Services;

import com.PFE.RH.DTO.EnfantDTO;
import com.PFE.RH.DTO.EnfantWithoutContactIdDTO;
import com.PFE.RH.Entities.Contact;
import com.PFE.RH.Entities.Enfant;
import com.PFE.RH.Mappers.EnfantMapper;
import com.PFE.RH.Mappers.EnfantWithoutContactIdMapper;
import com.PFE.RH.Repositories.ContactRepository;
import com.PFE.RH.Repositories.EnfantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnfantService {

    @Autowired
    private EnfantRepository enfantRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private EnfantMapper enfantMapper;
    @Autowired
      private EnfantWithoutContactIdMapper enfantWithoutContactIdMapper;

    public List<EnfantDTO> getAllEnfants() {
        List<Enfant> enfants = enfantRepository.findAll();
        return enfants.stream()
                .map(enfantMapper::enfantToEnfantDTO)
                .collect(Collectors.toList());
    }

    public EnfantDTO getEnfantById(Long id) {
        Optional<Enfant> enfantOptional = enfantRepository.findById(id);
        if (enfantOptional.isPresent()) {
            Enfant enfant = enfantOptional.get();
            return enfantMapper.enfantToEnfantDTO(enfant);
        } else {
            // Handle not found
            throw new RuntimeException("Enfant not found with ID: " + id);
        }
    }

    public EnfantDTO saveEnfant(EnfantDTO enfantDTO) {
        Enfant enfant = enfantMapper.enfantDTOToEnfant(enfantDTO);
        EnfantWithoutContactIdDTO enfantWithoutContactIdDTO=enfantWithoutContactIdMapper.enfantToEnfantWithoutContactIdDTO(enfant);
        // Fetch the Contact entity by contactId
        Contact contact = contactRepository.findById(enfantDTO.getContactId())
                .orElseThrow(() -> new RuntimeException("Contact not found with ID: " + enfantDTO.getContactId()));

        // Associate Enfant with Contact
        enfant.setContact(contact);
       // contact.setPassword(contact.getPassword());
        //contact.setRoles(contact.getRoles());
        //contact.setNumCompte(contact.getNumCompte());
        //contact.setFax(contact.getFax());
        contact.setNbEnfant(contact.getNbEnfant()+1);
        // Add Enfant to the list of Enfants in Contact
       contact.getEnfants().add(enfant);

        // Save Enfant
        Enfant savedEnfant = enfantRepository.save(enfant);



        return enfantMapper.enfantToEnfantDTO(savedEnfant);
    }


    public EnfantDTO updateEnfant(Long id, EnfantDTO enfantDTO) {
        Optional<Enfant> enfantOptional = enfantRepository.findById(id);
        if (enfantOptional.isPresent()) {
            Enfant existingEnfant = enfantOptional.get();
            existingEnfant.setName(enfantDTO.getName());
            existingEnfant.setFamilyName(enfantDTO.getFamilyName());
            existingEnfant.setAge(enfantDTO.getAge());
            existingEnfant.setDisabled(enfantDTO.isDisabled());
            existingEnfant.setBourse(enfantDTO.isBourse());
            existingEnfant.setEducationGrade(enfantDTO.getEducationGrade());
            Contact contact = contactRepository.findById(enfantDTO.getContactId())
                    .orElseThrow(() -> new RuntimeException("Contact not found with ID: " + enfantDTO.getContactId()));
            existingEnfant.setContact(contact);


            Enfant updatedEnfant = enfantRepository.save(existingEnfant);
            return enfantMapper.enfantToEnfantDTO(updatedEnfant);
        } else {
            // Handle not found
            throw new RuntimeException("Enfant not found with ID: " + id);
        }
    }

    public void deleteEnfantById(Long id) {
        enfantRepository.deleteById(id);
    }
}
