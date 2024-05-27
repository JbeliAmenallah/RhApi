package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.JourFerieDTO;
import com.PFE.RH.Entities.JourFerie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JourFerieMapper {
    JourFerieMapper INSTANCE = Mappers.getMapper(JourFerieMapper.class);

    @Mapping(target = "anneeId", source = "annee.id")
    JourFerieDTO toJourFerieDTO(JourFerie jourFerie);

    @Mapping(target = "annee", ignore = true)
    JourFerie toJourFerie(JourFerieDTO jourFerieDTO);
}
