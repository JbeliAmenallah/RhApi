package com.PFE.RH.Services;

import com.PFE.RH.DTO.CotisationDTO;
import com.PFE.RH.Entities.Contact;
import com.PFE.RH.Entities.Cotisation;
import com.PFE.RH.Mappers.CotisationMapper;
import com.PFE.RH.Repositories.CotisationRepository;
import com.PFE.RH.Repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class CotisationService {

    @Autowired
    private CotisationRepository cotisationRepository;

    @Autowired
    private CotisationMapper cotisationMapper;

    @Autowired
    private ContactRepository contactRepository;

    private static final Logger logger = LoggerFactory.getLogger(CotisationService.class);

    public CotisationDTO saveCotisation(CotisationDTO cotisationDTO) {
        // Log the incoming CotisationDTO
        logger.info("Incoming CotisationDTO: {}", cotisationDTO);

        // Map CotisationDTO to Cotisation
        Cotisation cotisation = cotisationMapper.cotisationDTOToCotisation(cotisationDTO);
        logger.info("Mapped Cotisation: {}", cotisation);

        // Retrieve Contact from repository
        Contact contact = contactRepository.findById(cotisationDTO.getContactId())
                .orElseThrow(() -> new IllegalArgumentException("Contact not found"));
        logger.info("Retrieved Contact: {}", contact);

        // Set Contact in Cotisation
        cotisation.setContact(contact);

        // Save Cotisation and map back to DTO
        Cotisation savedCotisation = cotisationRepository.save(cotisation);
        logger.info("Saved Cotisation: {}", savedCotisation);

        return cotisationMapper.cotisationToCotisationDTO(savedCotisation);
    }


    public CotisationDTO getCotisationById(Long cotisationId) {
        Optional<Cotisation> cotisationOptional = cotisationRepository.findById(cotisationId);
        return cotisationOptional.map(cotisationMapper::cotisationToCotisationDTO).orElse(null);
    }

    public CotisationDTO updateCotisation(Long cotisationId, CotisationDTO updatedCotisationDTO) {
        Optional<Cotisation> cotisationOptional = cotisationRepository.findById(cotisationId);
        if (cotisationOptional.isPresent()) {
            Cotisation cotisation = cotisationOptional.get();
            cotisation.setLibele(updatedCotisationDTO.getLibele());
            cotisation.setAnnee(updatedCotisationDTO.getAnnee());
            cotisation.setTaux(updatedCotisationDTO.getTaux());
            Optional<Contact> contactOptional = contactRepository.findById(updatedCotisationDTO.getContactId());
            if (contactOptional.isPresent()) {
                Contact contact = contactOptional.get();
                cotisation.setContact(contact);
                contactRepository.save(contact);
            }
            cotisationRepository.save(cotisation);
            return cotisationMapper.cotisationToCotisationDTO(cotisation);
        }
        return null;
    }

    public List<CotisationDTO> getAllCotisations() {
        List<Cotisation> cotisations = cotisationRepository.findAll();
        return cotisations.stream()
                .map(cotisationMapper::cotisationToCotisationDTO)
                .collect(Collectors.toList());
    }

    /*public boolean deleteCotisation(Long cotisationId) {
        Optional<Cotisation> cotisationOptional = cotisationRepository.findById(cotisationId);
        if (cotisationOptional.isPresent()) {
            Cotisation cotisation = cotisationOptional.get();
            cotisation.getContact().getCotisations().remove(cotisation);
            cotisationRepository.deleteById(cotisationId);
            return true;
        }
        return false;
    }*/
    public CotisationDTO patchCotisation(Long cotisationId, CotisationDTO patchedCotisationDTO) {
        Optional<Cotisation> cotisationOptional = cotisationRepository.findById(cotisationId);
        if (cotisationOptional.isPresent()) {
            Cotisation cotisation = cotisationOptional.get();

            if (patchedCotisationDTO.getLibele() != null) {
                cotisation.setLibele(patchedCotisationDTO.getLibele());
            }
            if (patchedCotisationDTO.getAnnee() != 0) {
                cotisation.setAnnee(patchedCotisationDTO.getAnnee());
            }
            if (patchedCotisationDTO.getTaux() != 0.0) {
                cotisation.setTaux(patchedCotisationDTO.getTaux());
            }
            if (patchedCotisationDTO.getContactId() != null) {
                Optional<Contact> contactOptional = contactRepository.findById(patchedCotisationDTO.getContactId());
                contactOptional.ifPresent(cotisation::setContact);
            }

            Cotisation updatedCotisation = cotisationRepository.save(cotisation);
            return cotisationMapper.cotisationToCotisationDTO(updatedCotisation);
        }
        return null;
    }
}
