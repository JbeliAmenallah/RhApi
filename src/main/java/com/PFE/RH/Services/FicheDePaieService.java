package com.PFE.RH.Services;

import com.PFE.RH.DTO.FicheDePaieDTO;
import com.PFE.RH.Entities.FicheDePaie;
import com.PFE.RH.Mappers.FicheDePaieMapper;
import com.PFE.RH.Repositories.FicheDePaieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FicheDePaieService {

    private final FicheDePaieRepository ficheDePaieRepository;
    private final FicheDePaieMapper ficheDePaieMapper;

    @Autowired
    public FicheDePaieService(FicheDePaieRepository ficheDePaieRepository, FicheDePaieMapper ficheDePaieMapper) {
        this.ficheDePaieRepository = ficheDePaieRepository;
        this.ficheDePaieMapper = ficheDePaieMapper;
    }
    public void persist(FicheDePaie ficheDePaie) {
        ficheDePaieRepository.save(ficheDePaie);
    }
    public List<FicheDePaieDTO> getFicheDePaieByContactId(Long contactId) {
        List<FicheDePaie> ficheDePaies = ficheDePaieRepository.findByContactContactId(contactId);
        // Map ficheDePaies to DTOs and return
        return ficheDePaies.stream()
                .map(ficheDePaieMapper::ficheDePaieToFicheDePaieDTO)
                .collect(Collectors.toList());
    }

    public List<FicheDePaieDTO> getAllFichesDePaie() {
        List<FicheDePaie> fichesDePaie = ficheDePaieRepository.findAll();
        return fichesDePaie.stream()
                .map(ficheDePaieMapper::ficheDePaieToFicheDePaieDTO)
                .collect(Collectors.toList());
    }

    public FicheDePaieDTO getFicheDePaieById(Long id) {
        Optional<FicheDePaie> optionalFicheDePaie = ficheDePaieRepository.findById(id);
        if (optionalFicheDePaie.isPresent()) {
            return ficheDePaieMapper.ficheDePaieToFicheDePaieDTO(optionalFicheDePaie.get());
        } else {
            throw new RuntimeException("Fiche de paie not found with ID: " + id);
        }
    }

    public FicheDePaieDTO saveFicheDePaie(FicheDePaieDTO ficheDePaieDTO) {
        FicheDePaie ficheDePaie = ficheDePaieMapper.ficheDePaieDTOToFicheDePaie(ficheDePaieDTO);
        FicheDePaie savedFicheDePaie = ficheDePaieRepository.save(ficheDePaie);
        return ficheDePaieMapper.ficheDePaieToFicheDePaieDTO(savedFicheDePaie);
    }



    public boolean deleteFicheDePaie(Long id) {
        Optional<FicheDePaie> optionalFicheDePaie = ficheDePaieRepository.findById(id);
        if (optionalFicheDePaie.isPresent()) {
            ficheDePaieRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Fiche de paie not found with ID: " + id);
        }
    }

   /* public List<FicheDePaieDTO> getFichesDePaieByContactId(Long contactId) {
        List<FicheDePaie> fichesDePaie = ficheDePaieRepository.findByContactId(contactId);
        return fichesDePaie.stream()
                .map(ficheDePaieMapper::ficheDePaieToFicheDePaieDTO)
                .collect(Collectors.toList());
    }*/

    public FicheDePaieDTO updatePartialFicheDePaie(Long id, FicheDePaieDTO updatedFicheDePaieDTO) {
        Optional<FicheDePaie> optionalFicheDePaie = ficheDePaieRepository.findById(id);
        if (optionalFicheDePaie.isPresent()) {
            FicheDePaie existingFicheDePaie = optionalFicheDePaie.get();

            // Check and update fields that are not null or not equal to default values
            if (updatedFicheDePaieDTO.getSalaireNet() != 0.0) {
                existingFicheDePaie.setSalaireNet(updatedFicheDePaieDTO.getSalaireNet());
            }
            if (updatedFicheDePaieDTO.getErpp() != 0.0) {
                existingFicheDePaie.setErpp(updatedFicheDePaieDTO.getErpp());
            }
            if (updatedFicheDePaieDTO.getNbrconge()!=0.0){
                existingFicheDePaie.setNbrconge(updatedFicheDePaieDTO.getNbrconge());
            }
            if (updatedFicheDePaieDTO.getCss() != 0.0) {
                existingFicheDePaie.setCss(updatedFicheDePaieDTO.getCss());
            }
            if (updatedFicheDePaieDTO.getPrime() != 0.0) {
                existingFicheDePaie.setPrime(updatedFicheDePaieDTO.getPrime());
            }
            if (updatedFicheDePaieDTO.getYear() != 0) {
                existingFicheDePaie.setYear(updatedFicheDePaieDTO.getYear());
            }
            if (updatedFicheDePaieDTO.getMonth() != 0) {
                existingFicheDePaie.setMonth(updatedFicheDePaieDTO.getMonth());
            }

            FicheDePaie updatedFicheDePaie = ficheDePaieRepository.save(existingFicheDePaie);
            return ficheDePaieMapper.ficheDePaieToFicheDePaieDTO(updatedFicheDePaie);
        } else {
            throw new RuntimeException("Fiche de paie not found with ID: " + id);
        }
    }


}
