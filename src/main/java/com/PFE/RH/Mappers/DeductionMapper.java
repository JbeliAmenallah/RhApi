package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.DeductionDTO;
import com.PFE.RH.Entities.Deduction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeductionMapper {

    DeductionMapper INSTANCE = Mappers.getMapper(DeductionMapper.class);

    @Mapping(target = "anneeId", source = "deduction.annee.id")
    DeductionDTO toDeductionDTO(Deduction deduction);

    @Mapping(target = "annee.id", source = "deductionDTO.anneeId")
    Deduction toDeduction(DeductionDTO deductionDTO);
}
