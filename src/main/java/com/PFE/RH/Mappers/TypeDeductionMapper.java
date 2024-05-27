package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.TypeDeductionDTO;
import com.PFE.RH.Entities.TypeDeduction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TypeDeductionMapper {

    TypeDeductionMapper INSTANCE = Mappers.getMapper(TypeDeductionMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "ref", target = "ref")
    @Mapping(source = "designation", target = "designation")
    @Mapping(source = "commune", target = "commune")
    @Mapping(source = "montant", target = "montant")
    @Mapping(source = "pourcentage", target = "pourcentage")
    TypeDeductionDTO toTypeDeductionDTO(TypeDeduction typeDeduction);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "ref", target = "ref")
    @Mapping(source = "designation", target = "designation")
    @Mapping(source = "commune", target = "commune")
    @Mapping(source = "montant", target = "montant")
    @Mapping(source = "pourcentage", target = "pourcentage")
    TypeDeduction toTypeDeduction(TypeDeductionDTO typeDeductionDTO);
}
