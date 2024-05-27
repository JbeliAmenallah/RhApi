package com.PFE.RH.Services;

import com.PFE.RH.DTO.TypeDeductionDTO;
import com.PFE.RH.Entities.TypeDeduction;
import com.PFE.RH.Mappers.TypeDeductionMapper;
import com.PFE.RH.Repositories.TypeDeductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TypeDeductionService {

    @Autowired
    private TypeDeductionRepository typeDeductionRepository;

    @Autowired
    private TypeDeductionMapper typeDeductionMapper;

    public List<TypeDeductionDTO> getAllTypeDeductions() {
        List<TypeDeduction> typeDeductions = typeDeductionRepository.findAll();
        return typeDeductions.stream()
                .map(typeDeductionMapper::toTypeDeductionDTO)
                .collect(Collectors.toList());
    }

    public TypeDeductionDTO getTypeDeductionById(Long id) {
        TypeDeduction typeDeduction = typeDeductionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeDeduction not found with id: " + id));
        return typeDeductionMapper.toTypeDeductionDTO(typeDeduction);
    }

    public TypeDeductionDTO saveTypeDeduction(TypeDeductionDTO typeDeductionDTO) {
        TypeDeduction typeDeduction = typeDeductionMapper.toTypeDeduction(typeDeductionDTO);
        TypeDeduction savedTypeDeduction = typeDeductionRepository.save(typeDeduction);
        return typeDeductionMapper.toTypeDeductionDTO(savedTypeDeduction);
    }

    public TypeDeductionDTO updateTypeDeduction(Long id, TypeDeductionDTO updatedTypeDeductionDTO) {
        Optional<TypeDeduction> optionalTypeDeduction = typeDeductionRepository.findById(id);
        if (optionalTypeDeduction.isPresent()) {
            TypeDeduction typeDeduction = optionalTypeDeduction.get();
            typeDeduction.setRef(updatedTypeDeductionDTO.getRef());
            typeDeduction.setDesignation(updatedTypeDeductionDTO.getDesignation());
            typeDeduction.setCommune(updatedTypeDeductionDTO.getCommune());
            typeDeduction.setMontant(updatedTypeDeductionDTO.getMontant());
            typeDeduction.setPourcentage(updatedTypeDeductionDTO.getPourcentage());
            TypeDeduction updatedTypeDeduction = typeDeductionRepository.save(typeDeduction);
            return typeDeductionMapper.toTypeDeductionDTO(updatedTypeDeduction);
        } else {
            throw new RuntimeException("TypeDeduction not found with id: " + id);
        }
    }

    public TypeDeductionDTO patchTypeDeduction(Long id, TypeDeductionDTO patchedTypeDeductionDTO) {
        Optional<TypeDeduction> optionalTypeDeduction = typeDeductionRepository.findById(id);
        if (optionalTypeDeduction.isPresent()) {
            TypeDeduction typeDeduction = optionalTypeDeduction.get();
            if (patchedTypeDeductionDTO.getRef() != null) {
                typeDeduction.setRef(patchedTypeDeductionDTO.getRef());
            }
            if (patchedTypeDeductionDTO.getDesignation() != null) {
                typeDeduction.setDesignation(patchedTypeDeductionDTO.getDesignation());
            }
            if (patchedTypeDeductionDTO.getCommune() != "") {
                typeDeduction.setCommune(patchedTypeDeductionDTO.getCommune());
            }
            if (patchedTypeDeductionDTO.getMontant() != 0) {
                typeDeduction.setMontant(patchedTypeDeductionDTO.getMontant());
            }
            if (patchedTypeDeductionDTO.getPourcentage() != 0) {
                typeDeduction.setPourcentage(patchedTypeDeductionDTO.getPourcentage());
            }
            TypeDeduction patchedTypeDeduction = typeDeductionRepository.save(typeDeduction);
            return typeDeductionMapper.toTypeDeductionDTO(patchedTypeDeduction);
        } else {
            throw new RuntimeException("TypeDeduction not found with id: " + id);
        }
    }

    public boolean deleteTypeDeduction(Long id) {
        if (typeDeductionRepository.existsById(id)) {
            typeDeductionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

