package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.CongeDTO;
import com.PFE.RH.Entities.Conge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CongeMapper {

    CongeMapper INSTANCE = Mappers.getMapper(CongeMapper.class);

    @Mappings({
            @Mapping(source = "conge.congeId", target = "congeId"),
            @Mapping(source = "conge.startDate", target = "startDate"),
            @Mapping(source = "conge.endDate", target = "endDate"),
            @Mapping(source = "conge.state", target = "state"),
            @Mapping(source = "conge.contact.contactId", target = "contactId") // Use "contact.contactId" here
    })
    CongeDTO congeToCongeDTO(Conge conge);

    @Mappings({
            @Mapping(source = "congeDTO.congeId", target = "congeId"),
            @Mapping(source = "congeDTO.startDate", target = "startDate"),
            @Mapping(source = "congeDTO.endDate", target = "endDate"),
            @Mapping(source = "congeDTO.state", target = "state"),
            @Mapping(source = "congeDTO.contactId", target = "contact.contactId") // Use "contact.contactId" here
    })
    Conge congeDTOToConge(CongeDTO congeDTO);
}
