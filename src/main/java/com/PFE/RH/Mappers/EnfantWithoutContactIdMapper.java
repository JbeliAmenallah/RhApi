package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.EnfantWithoutContactIdDTO;
import com.PFE.RH.Entities.Enfant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnfantWithoutContactIdMapper {

    @Mapping(target = "contact", ignore = true)
    Enfant enfantWithoutContactIdDTOToEnfant(EnfantWithoutContactIdDTO enfantDTO);

    EnfantWithoutContactIdDTO enfantToEnfantWithoutContactIdDTO(Enfant enfant);
}
