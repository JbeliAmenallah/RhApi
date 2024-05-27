package com.PFE.RH.Services;

import com.PFE.RH.DTO.GradeDTO;
import com.PFE.RH.Entities.Grade;
import com.PFE.RH.Mappers.GradeMapper;
import com.PFE.RH.Repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private GradeMapper gradeMapper;

    public List<GradeDTO> getAllGrades() {
        List<Grade> grades = gradeRepository.findAll();
        return grades.stream()
                .map(gradeMapper::gradeToGradeDTO)
                .collect(Collectors.toList());
    }

    public GradeDTO saveGrade(GradeDTO gradeDTO) {
        Grade grade = gradeMapper.gradeDTOToGrade(gradeDTO);
        Grade savedGrade = gradeRepository.save(grade);
        return gradeMapper.gradeToGradeDTO(savedGrade);
    }

    public GradeDTO getGradeById(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new RuntimeException("Grade not found with id: " + gradeId));
        return gradeMapper.gradeToGradeDTO(grade);
    }

    public GradeDTO updateGrade(Long gradeId, GradeDTO updatedGradeDTO) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new RuntimeException("Grade not found with id: " + gradeId));

        grade.setLibele(updatedGradeDTO.getLibele());

        Grade updatedGrade = gradeRepository.save(grade);
        return gradeMapper.gradeToGradeDTO(updatedGrade);
    }

    public void deleteGrade(Long gradeId) {
        gradeRepository.deleteById(gradeId);
    }

    public List<GradeDTO> getByGradeLibele(String libele) {
        List<Grade> grades = gradeRepository.findByLibele(libele);
        return grades.stream()
                .map(gradeMapper::gradeToGradeDTO)
                .collect(Collectors.toList());
    }
    public GradeDTO updatePartialGrade(Long gradeId, GradeDTO updatedGradeDTO) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new RuntimeException("Grade not found with id: " + gradeId));

        if (updatedGradeDTO.getLibele() != null) {
            grade.setLibele(updatedGradeDTO.getLibele());
        }

        Grade updatedGrade = gradeRepository.save(grade);
        return gradeMapper.gradeToGradeDTO(updatedGrade);
    }

}
