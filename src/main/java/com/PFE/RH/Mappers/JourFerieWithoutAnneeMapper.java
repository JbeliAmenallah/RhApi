package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.JourFerieWithoutAnneeDTO;
import com.PFE.RH.Entities.JourFerie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface JourFerieWithoutAnneeMapper {

    JourFerieWithoutAnneeMapper INSTANCE = Mappers.getMapper(JourFerieWithoutAnneeMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "jour", target = "jour"),
            @Mapping(source = "mois", target = "mois"),
            @Mapping(source = "libele", target = "libele")
    })
    JourFerie toJourFerie(JourFerieWithoutAnneeDTO jourFerieDTO);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "jour", target = "jour"),
            @Mapping(source = "mois", target = "mois"),
            @Mapping(source = "libele", target = "libele")
    })
    JourFerieWithoutAnneeDTO toJourFerieDTO(JourFerie jourFerie);
}
