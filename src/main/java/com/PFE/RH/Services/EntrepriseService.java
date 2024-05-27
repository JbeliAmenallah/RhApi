package com.PFE.RH.Services;

import com.PFE.RH.DTO.*;
import com.PFE.RH.Mappers.*;
import com.PFE.RH.Entities.Category;
import com.PFE.RH.Entities.Entreprise;
import com.PFE.RH.Entities.Grade;
import com.PFE.RH.Entities.Groupe;
import com.PFE.RH.Mappers.*;
import com.PFE.RH.Repositories.CategoryRepository;
import com.PFE.RH.Repositories.EntrepriseRepository;
import com.PFE.RH.Repositories.GradeRepository;
import com.PFE.RH.Repositories.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;
    private final EntrepriseMapper entrepriseMapper;
    private final EntrepriseWithoutContactsMapper entrepriseWithoutContactsMapper;

    private final GradeMapper gradeMapper;
    private final GroupeMapper groupeMapper;
    private final CategoryMapper categoryMapper;
    private final GradeRepository gradeRepository;
    private final GroupeRepository groupeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public EntrepriseService(EntrepriseRepository entrepriseRepository, EntrepriseMapper entrepriseMapper,GroupeRepository groupeRepository,
                             GradeMapper gradeMapper, GroupeMapper groupeMapper, CategoryMapper categoryMapper,GradeRepository gradeRepository,
                             CategoryRepository categoryRepository,EntrepriseWithoutContactsMapper entrepriseWithoutContactsMapper) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        this.gradeMapper = gradeMapper;
        this.groupeMapper = groupeMapper;
        this.categoryMapper = categoryMapper;
        this.gradeRepository=gradeRepository;
        this.groupeRepository=groupeRepository;
        this.categoryRepository=categoryRepository;
        this.entrepriseWithoutContactsMapper=entrepriseWithoutContactsMapper;
    }

    public List<EntrepriseDTO> getAllEntreprises() {
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        return entreprises.stream()
                .map(entrepriseMapper::entrepriseToEntrepriseDTO)
                .collect(Collectors.toList());
    }

    public EntrepriseDTO getEntrepriseById(Long id) {
        Optional<Entreprise> entrepriseOptional = entrepriseRepository.findById(id);
        if (entrepriseOptional.isPresent()) {
            return entrepriseMapper.entrepriseToEntrepriseDTO(entrepriseOptional.get());
        } else {
            throw new RuntimeException("Entreprise not found with ID: " + id);
        }
    }

    public EntrepriseDTO saveEntreprise(EntrepriseDTO entrepriseDTO) {
        Entreprise entreprise = entrepriseMapper.entrepriseDTOToEntreprise(entrepriseDTO);
        Entreprise savedEntreprise = entrepriseRepository.save(entreprise);
        return entrepriseMapper.entrepriseToEntrepriseDTO(savedEntreprise);
    }

    public EntrepriseDTO updateEntreprise(Long id, EntrepriseDTO updatedEntrepriseDTO) {
        Optional<Entreprise> optionalEntreprise = entrepriseRepository.findById(id);
        if (optionalEntreprise.isPresent()) {
            Entreprise existingEntreprise = optionalEntreprise.get();
            existingEntreprise.setNom(updatedEntrepriseDTO.getNom());
            existingEntreprise.setMatricule(updatedEntrepriseDTO.getMatricule());
            existingEntreprise.setSiegesociale(updatedEntrepriseDTO.getSiegesociale());
            existingEntreprise.setRaisonSociale(updatedEntrepriseDTO.getRaisonSociale());
            existingEntreprise.setAdresseDeSiege(updatedEntrepriseDTO.getAdresseDeSiege());
            existingEntreprise.setMatriculeFiscale(updatedEntrepriseDTO.getMatriculeFiscale());
            existingEntreprise.setNumCnss(updatedEntrepriseDTO.getNumCnss());
            existingEntreprise.setRegimeSalariale(updatedEntrepriseDTO.getRegimeSalariale());
            existingEntreprise.setNbrJourConge(updatedEntrepriseDTO.getNbrJourConge());

            Entreprise updatedEntreprise = entrepriseRepository.save(existingEntreprise);
            return entrepriseMapper.entrepriseToEntrepriseDTO(updatedEntreprise);
        } else {
            throw new RuntimeException("Entreprise not found with ID: " + id);
        }
    }

    public boolean deleteEntreprise(Long id) {
        Optional<Entreprise> optionalEntreprise = entrepriseRepository.findById(id);
        if (optionalEntreprise.isPresent()) {
            entrepriseRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Entreprise not found with ID: " + id);
        }
    }

    public EntrepriseWithoutContactsDTO addGradeToEntreprise(Long entrepriseId, Long gradeId) {
        Optional<Entreprise> optionalEntreprise = entrepriseRepository.findById(entrepriseId);
        if (optionalEntreprise.isPresent()) {
            Entreprise entreprise = optionalEntreprise.get();

            Optional<Grade> optionalGrade = gradeRepository.findById(gradeId);
            if (optionalGrade.isPresent()) {
                Grade grade = optionalGrade.get();
                entreprise.getGrades().add(grade);

                Entreprise updatedEntreprise = entrepriseRepository.save(entreprise);
                return entrepriseWithoutContactsMapper.entrepriseToEntrepriseWithoutContactsDTO(updatedEntreprise);
            } else {
                throw new RuntimeException("Grade not found with ID: " + gradeId);
            }
        } else {
            throw new RuntimeException("Entreprise not found with ID: " + entrepriseId);
        }
    }

    public EntrepriseWithoutContactsDTO addGroupeToEntreprise(Long entrepriseId, Long groupeId) {
        Optional<Entreprise> optionalEntreprise = entrepriseRepository.findById(entrepriseId);
        if (optionalEntreprise.isPresent()) {
            Entreprise entreprise = optionalEntreprise.get();

            Optional<Groupe> optionalGroupe = groupeRepository.findById(groupeId);
            if (optionalGroupe.isPresent()) {
                Groupe groupe = optionalGroupe.get();
                entreprise.getGroupes().add(groupe);

                Entreprise updatedEntreprise = entrepriseRepository.save(entreprise);
                return entrepriseWithoutContactsMapper.entrepriseToEntrepriseWithoutContactsDTO(updatedEntreprise);
            } else {
                throw new RuntimeException("Groupe not found with ID: " + groupeId);
            }
        } else {
            throw new RuntimeException("Entreprise not found with ID: " + entrepriseId);
        }
    }

    public EntrepriseWithoutContactsDTO addCategoryToEntreprise(Long entrepriseId, Long categoryId) {
        Optional<Entreprise> optionalEntreprise = entrepriseRepository.findById(entrepriseId);
        if (optionalEntreprise.isPresent()) {
            Entreprise entreprise = optionalEntreprise.get();

            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                entreprise.getCategories().add(category);

                Entreprise updatedEntreprise = entrepriseRepository.save(entreprise);
                return entrepriseWithoutContactsMapper.entrepriseToEntrepriseWithoutContactsDTO(updatedEntreprise);
            } else {
                throw new RuntimeException("Category not found with ID: " + categoryId);
            }
        } else {
            throw new RuntimeException("Entreprise not found with ID: " + entrepriseId);
        }
    }

}
