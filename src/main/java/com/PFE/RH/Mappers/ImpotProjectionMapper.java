package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.ImpotProjectionDTO;
import com.PFE.RH.Entities.Impot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImpotProjectionMapper {

    ImpotProjectionMapper INSTANCE = Mappers.getMapper(ImpotProjectionMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "libele", target = "libele")
    @Mapping(source = "taux", target = "taux")
    ImpotProjectionDTO toDto(Impot impot);
}
