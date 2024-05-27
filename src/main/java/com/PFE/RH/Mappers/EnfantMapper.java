package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.EnfantDTO;
import com.PFE.RH.Entities.Enfant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnfantMapper {

    @Mapping(target = "contactId", source = "enfant.contact.contactId")
    EnfantDTO enfantToEnfantDTO(Enfant enfant);

    @Mapping(target = "contact.contactId", source = "enfantDTO.contactId")
    Enfant enfantDTOToEnfant(EnfantDTO enfantDTO);
}
