package com.PFE.RH.Services;

import com.PFE.RH.DTO.AnneeWithoutJourFerieDTO;
import com.PFE.RH.DTO.JourFerieDTO;
import com.PFE.RH.Entities.Annee;
import com.PFE.RH.Entities.JourFerie;
import com.PFE.RH.Mappers.JourFerieMapper;
import com.PFE.RH.Repositories.JourFerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JourFerieService {

    @Autowired
    private JourFerieRepository jourFerieRepository;

    @Autowired
    private JourFerieMapper jourFerieMapper;

    @Autowired
    private AnneeService anneeService;

    public List<JourFerieDTO> getAllJourFeries() {
        List<JourFerie> jourFeries = jourFerieRepository.findAll();
        return jourFeries.stream()
                .map(jourFerieMapper::toJourFerieDTO)
                .collect(Collectors.toList());
    }

    public JourFerieDTO saveJourFerie(JourFerieDTO jourFerieDTO) {
        Annee annee = mapAnneeWithoutJourFerieDTO(jourFerieDTO.getAnneeId());
        JourFerie jourFerie = jourFerieMapper.toJourFerie(jourFerieDTO);
        jourFerie.setAnnee(annee);
        JourFerie savedJourFerie = jourFerieRepository.save(jourFerie);
        return jourFerieMapper.toJourFerieDTO(savedJourFerie);
    }

    public JourFerieDTO updateJourFerie(Long id, JourFerieDTO updatedJourFerieDTO) {
        Optional<JourFerie> optionalJourFerie = jourFerieRepository.findById(id);
        if (optionalJourFerie.isPresent()) {
            JourFerie jourFerie = optionalJourFerie.get();
            jourFerie.setJour(updatedJourFerieDTO.getJour());
            jourFerie.setMois(updatedJourFerieDTO.getMois());
            jourFerie.setLibele(updatedJourFerieDTO.getLibele());
            jourFerie.setAnnee(mapAnneeWithoutJourFerieDTO(updatedJourFerieDTO.getAnneeId()));
            // You may need to update other fields as needed
            JourFerie updatedJourFerie = jourFerieRepository.save(jourFerie);
            return jourFerieMapper.toJourFerieDTO(updatedJourFerie);
        } else {
            throw new RuntimeException("JourFerie not found with id: " + id);
        }
    }

    public JourFerieDTO partialUpdateJourFerie(Long id, JourFerieDTO partialJourFerieDTO) {
        Optional<JourFerie> optionalJourFerie = jourFerieRepository.findById(id);
        if (optionalJourFerie.isPresent()) {
            JourFerie jourFerie = optionalJourFerie.get();

            if (partialJourFerieDTO.getJour() != 0) {
                jourFerie.setJour(partialJourFerieDTO.getJour());
            }
            if (partialJourFerieDTO.getMois() != 0) {
                jourFerie.setMois(partialJourFerieDTO.getMois());
            }

            if (partialJourFerieDTO.getLibele() != null) {
                jourFerie.setLibele(partialJourFerieDTO.getLibele());
            }
            if (partialJourFerieDTO.getAnneeId() != null) {
                jourFerie.setAnnee(mapAnneeWithoutJourFerieDTO(partialJourFerieDTO.getAnneeId()));
            }

            // You may need to update other fields as needed
            JourFerie updatedJourFerie = jourFerieRepository.save(jourFerie);
            return jourFerieMapper.toJourFerieDTO(updatedJourFerie);
        } else {
            throw new RuntimeException("JourFerie not found with id: " + id);
        }
    }

    public boolean deleteJourFerie(Long id) {
        if (jourFerieRepository.existsById(id)) {
            jourFerieRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private Annee mapAnneeWithoutJourFerieDTO(Long anneeId) {
        AnneeWithoutJourFerieDTO anneeWithoutJourFerieDTO = anneeService.getAnneeById(anneeId);
        Annee annee = new Annee();
        annee.setId(anneeWithoutJourFerieDTO.getId());
        annee.setDateDebutExercice(anneeWithoutJourFerieDTO.getDateDebutExercice());
        annee.setLibele(anneeWithoutJourFerieDTO.getLibele());
        return annee;
    }
}
