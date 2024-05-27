package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.AutorisationDTO;
import com.PFE.RH.Entities.Autorisation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutorisationMapper {

    AutorisationMapper INSTANCE = Mappers.getMapper(AutorisationMapper.class);

    @Mappings({
            @Mapping(source = "autorisationId", target = "autorisationId"),
            @Mapping(source = "dateDebut", target = "dateDebut"),
            @Mapping(source = "dateFin", target = "dateFin"),
            @Mapping(source = "contact.contactId", target = "contactId"),
            @Mapping(source = "state", target = "state")
    })
    AutorisationDTO autorisationToAutorisationDTO(Autorisation autorisation);

    @Mappings({
            @Mapping(source = "autorisationId", target = "autorisationId"),
            @Mapping(source = "dateDebut", target = "dateDebut"),
            @Mapping(source = "dateFin", target = "dateFin"),
            @Mapping(source = "contactId", target = "contact.contactId"),
            @Mapping(target = "contact", ignore = true), // Ignore mapping for Contact
            @Mapping(source = "state", target = "state")
    })
    Autorisation autorisationDTOToAutorisation(AutorisationDTO autorisationDTO);

}
