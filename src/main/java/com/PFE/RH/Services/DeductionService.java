package com.PFE.RH.Services;

import com.PFE.RH.DTO.DeductionDTO;
import com.PFE.RH.Entities.Annee;
import com.PFE.RH.Entities.Deduction;
import com.PFE.RH.Mappers.DeductionMapper;
import com.PFE.RH.Repositories.AnneeRepository;
import com.PFE.RH.Repositories.DeductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeductionService {

    @Autowired
    private DeductionRepository deductionRepository;

    @Autowired
    private AnneeRepository anneeRepository;

    @Autowired
    private DeductionMapper deductionMapper;

    public List<DeductionDTO> getAllDeductions() {
        List<Deduction> deductions = deductionRepository.findAll();
        return deductions.stream()
                .map(deductionMapper::toDeductionDTO)
                .collect(Collectors.toList());
    }

    public DeductionDTO getDeductionById(Long id) {
        Deduction deduction = getDeductionEntityById(id);
        return deductionMapper.toDeductionDTO(deduction);
    }

    public DeductionDTO saveDeduction(DeductionDTO deductionDTO) {
        Deduction deduction = deductionMapper.toDeduction(deductionDTO);
        Annee annee = getAnneeById(deductionDTO.getAnneeId());
        deduction.setAnnee(annee);
        Deduction savedDeduction = deductionRepository.save(deduction);
        return deductionMapper.toDeductionDTO(savedDeduction);
    }

    public DeductionDTO updateDeduction(Long id, DeductionDTO updatedDeductionDTO) {
        Deduction existingDeduction = getDeductionEntityById(id);
        existingDeduction.setLibelle(updatedDeductionDTO.getLibelle());
        existingDeduction.setDescription(updatedDeductionDTO.getDescription());
        existingDeduction.setEtat(updatedDeductionDTO.getEtat());
        existingDeduction.setTypecalcul(updatedDeductionDTO.getTypecalcul());
        existingDeduction.setValeur(updatedDeductionDTO.getValeur());
        Annee annee = getAnneeById(updatedDeductionDTO.getAnneeId());
        existingDeduction.setAnnee(annee);
        Deduction updatedDeduction = deductionRepository.save(existingDeduction);
        return deductionMapper.toDeductionDTO(updatedDeduction);
    }

    public DeductionDTO patchDeduction(Long id, DeductionDTO patchedDeductionDTO) {
        Deduction deduction = getDeductionEntityById(id);
        if (deduction == null) {
            throw new RuntimeException("Deduction not found with id: " + id);
        }
        if (patchedDeductionDTO.getLibelle() == null &&
                patchedDeductionDTO.getDescription() == null &&
                patchedDeductionDTO.getEtat() == null &&
                patchedDeductionDTO.getTypecalcul() == null &&
                patchedDeductionDTO.getValeur() == null &&
                patchedDeductionDTO.getAnneeId() == null) {
            throw new IllegalArgumentException("At least one field must be provided for update.");
        }
        if (patchedDeductionDTO.getLibelle() != null) {
            deduction.setLibelle(patchedDeductionDTO.getLibelle());
        }
        if (patchedDeductionDTO.getDescription() != null) {
            deduction.setDescription(patchedDeductionDTO.getDescription());
        }
        if (patchedDeductionDTO.getEtat() != null) {
            deduction.setEtat(patchedDeductionDTO.getEtat());
        }
        if (patchedDeductionDTO.getTypecalcul() != null) {
            deduction.setTypecalcul(patchedDeductionDTO.getTypecalcul());
        }
        if (patchedDeductionDTO.getValeur() != null) {
            deduction.setValeur(patchedDeductionDTO.getValeur());
        }
        if (patchedDeductionDTO.getAnneeId() != null) {
            Annee annee = getAnneeById(patchedDeductionDTO.getAnneeId());
            deduction.setAnnee(annee);
        }
        Deduction updatedDeduction = deductionRepository.save(deduction);
        return deductionMapper.toDeductionDTO(updatedDeduction);
    }

    public boolean deleteDeduction(Long id) {
        if (deductionRepository.existsById(id)) {
            deductionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private Deduction getDeductionEntityById(Long id) {
        return deductionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deduction not found with id: " + id));
    }

    private Annee getAnneeById(Long id) {
        return anneeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annee not found with id: " + id));
    }
}
