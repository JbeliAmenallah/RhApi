package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.AnneeWithoutJourFerieDTO;
import com.PFE.RH.Entities.Annee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnneeWithoutJourFerieMapper {

    @Mapping(target = "id") // Ignore mapping ID
    AnneeWithoutJourFerieDTO toAnneeWithoutJourFerieDTO(Annee annee);
}
