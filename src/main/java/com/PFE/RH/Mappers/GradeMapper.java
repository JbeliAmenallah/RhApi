package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.GradeDTO;
import com.PFE.RH.Entities.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GradeMapper {
    GradeMapper INSTANCE = Mappers.getMapper(GradeMapper.class);

    GradeDTO gradeToGradeDTO(Grade grade);

    Grade gradeDTOToGrade(GradeDTO gradeDTO);
}
