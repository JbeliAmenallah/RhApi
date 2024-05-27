package com.PFE.RH.Services;

import com.PFE.RH.DTO.AnneeDTO;
import com.PFE.RH.DTO.AnneeWithoutJourFerieDTO;
import com.PFE.RH.Entities.Annee;
import com.PFE.RH.Mappers.AnneeMapper;
import com.PFE.RH.Mappers.AnneeWithoutJourFerieMapper;
import com.PFE.RH.Repositories.AnneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnneeService {

    @Autowired
    private AnneeRepository anneeRepository;

    @Autowired
    private AnneeMapper anneeMapper;

    @Autowired
    private AnneeWithoutJourFerieMapper anneeWithoutJourFerieMapper;

    public List<AnneeWithoutJourFerieDTO> getAllAnnees() {
        List<Annee> annees = anneeRepository.findAll();
        return annees.stream()
                .map(anneeWithoutJourFerieMapper::toAnneeWithoutJourFerieDTO)
                .collect(Collectors.toList());
    }

    public AnneeWithoutJourFerieDTO saveAnnee(AnneeDTO anneeDTO) {
        Annee annee = anneeMapper.toAnnee(anneeDTO);
        Annee savedAnnee = anneeRepository.save(annee);
        return anneeWithoutJourFerieMapper.toAnneeWithoutJourFerieDTO(savedAnnee);
    }

    public AnneeWithoutJourFerieDTO updateAnnee(Long id, AnneeDTO updatedAnneeDTO) {
        Optional<Annee> optionalAnnee = anneeRepository.findById(id);
        if (optionalAnnee.isPresent()) {
            Annee annee = optionalAnnee.get();
            annee.setDateDebutExercice(updatedAnneeDTO.getDateDebutExercice());
            annee.setLibele(updatedAnneeDTO.getLibele());
            Annee updatedAnnee = anneeRepository.save(annee);
            return anneeWithoutJourFerieMapper.toAnneeWithoutJourFerieDTO(updatedAnnee);
        } else {
            throw new RuntimeException("Annee not found with id: " + id);
        }
    }

    public boolean deleteAnnee(Long id) {
        if (anneeRepository.existsById(id)) {
            anneeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public AnneeWithoutJourFerieDTO getAnneeById(Long id) {
        Annee annee = anneeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annee not found with id: " + id));
        return anneeWithoutJourFerieMapper.toAnneeWithoutJourFerieDTO(annee);
    }
    public AnneeWithoutJourFerieDTO patchAnnee(Long id, AnneeDTO updatedAnneeDTO) {
        Optional<Annee> optionalAnnee = anneeRepository.findById(id);
        if (optionalAnnee.isPresent()) {
            Annee annee = optionalAnnee.get();
            if (updatedAnneeDTO.getDateDebutExercice() != null) {
                annee.setDateDebutExercice(updatedAnneeDTO.getDateDebutExercice());
            }
            if (updatedAnneeDTO.getLibele() != null) {
                annee.setLibele(updatedAnneeDTO.getLibele());
            }
            Annee patchedAnnee = anneeRepository.save(annee);
            return anneeWithoutJourFerieMapper.toAnneeWithoutJourFerieDTO(patchedAnnee);
        } else {
            throw new RuntimeException("Annee not found with id: " + id);
        }
    }
}
