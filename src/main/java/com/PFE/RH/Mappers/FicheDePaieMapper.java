package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.FicheDePaieDTO;
import com.PFE.RH.Entities.FicheDePaie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FicheDePaieMapper {

    @Mapping(target = "contactId", source = "ficheDePaie.contact.contactId")
    FicheDePaieDTO ficheDePaieToFicheDePaieDTO(FicheDePaie ficheDePaie);

    @Mapping(target = "contact.contactId", source = "ficheDePaieDTO.contactId")
    FicheDePaie ficheDePaieDTOToFicheDePaie(FicheDePaieDTO ficheDePaieDTO);
}
