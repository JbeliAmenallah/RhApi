package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.ImpotDTO;
import com.PFE.RH.DTO.AnneeWithoutJourFerieDTO;
import com.PFE.RH.Entities.Annee;
import com.PFE.RH.Entities.Impot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = AnneeMapper.class)
public interface ImpotMapper {

    ImpotMapper INSTANCE = Mappers.getMapper(ImpotMapper.class);

    @Mapping(source = "annee", target = "anneeWithoutJourFerieDTO")
    ImpotDTO toImpotDTO(Impot impot);

    Impot toImpot(ImpotDTO impotDTO);

    Annee toAnnee(ImpotDTO impotDTO);

    ImpotDTO toAnneeDTO(Annee annee);

    // Mapping for AnneeWithoutJourFerieDTO
    AnneeWithoutJourFerieDTO toAnneeWithoutJourFerieDTO(Annee annee);

}
