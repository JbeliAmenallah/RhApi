package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.AnneeDTO;
import com.PFE.RH.Entities.Annee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnneeMapper {

    AnneeMapper INSTANCE = Mappers.getMapper(AnneeMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "dateDebutExercice", target = "dateDebutExercice"),
            @Mapping(source = "libele", target = "libele"),
         @Mapping(source = "jourFeries", target = "jourFerieDTOs") // Map jourFeries to jourFerieDTOs
    })
    AnneeDTO toAnneeDTO(Annee annee);

    @Mappings({
            @Mapping(source = "anneeDTO.id", target = "id"),
            @Mapping(source = "anneeDTO.dateDebutExercice", target = "dateDebutExercice"),
            @Mapping(source = "anneeDTO.libele", target = "libele"),
          @Mapping(source = "anneeDTO.jourFerieDTOs", target = "jourFeries") // Map jourFerieDTOs to jourFeries
    })
    Annee toAnnee(AnneeDTO anneeDTO);
}
