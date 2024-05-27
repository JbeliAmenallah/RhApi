package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.CotisationDTO;
import com.PFE.RH.Entities.Cotisation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CotisationMapper {

    CotisationMapper INSTANCE = Mappers.getMapper(CotisationMapper.class);

    @Mapping(source = "cotisation.cotisationId", target = "cotisationId")
    @Mapping(source = "cotisation.libele", target = "libele")
    @Mapping(source = "cotisation.annee", target = "annee")
    @Mapping(source = "cotisation.taux", target = "taux")
    @Mapping(target = "contactId", expression = "java(cotisation.getContact() != null ? cotisation.getContact().getContactId() : null)")
    CotisationDTO cotisationToCotisationDTO(Cotisation cotisation);

    @Mapping(source = "cotisationDTO.cotisationId", target = "cotisationId")
    @Mapping(source = "cotisationDTO.libele", target = "libele")
    @Mapping(source = "cotisationDTO.annee", target = "annee")
    @Mapping(source = "cotisationDTO.taux", target = "taux")
    @Mapping(target = "contact", ignore = true) // Ignore contact mapping
    Cotisation cotisationDTOToCotisation(CotisationDTO cotisationDTO);
}
