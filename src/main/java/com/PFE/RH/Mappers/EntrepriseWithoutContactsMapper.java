package com.PFE.RH.Mappers;

import com.PFE.RH.Entities.Entreprise;
import com.PFE.RH.DTO.EntrepriseWithoutContactsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntrepriseWithoutContactsMapper {

    EntrepriseWithoutContactsMapper INSTANCE = Mappers.getMapper(EntrepriseWithoutContactsMapper.class);

    @Mapping(source = "entrepriseId", target = "entrepriseId")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "matricule", target = "matricule")
    @Mapping(source = "siegesociale", target = "siegesociale")
    @Mapping(source = "raisonSociale", target = "raisonSociale")
    @Mapping(source = "adresseDeSiege", target = "adresseDeSiege")
    @Mapping(source = "matriculeFiscale", target = "matriculeFiscale")
    @Mapping(source = "numCnss", target = "numCnss")
    @Mapping(source = "regimeSalariale", target = "regimeSalariale")
    @Mapping(source = "nbrJourConge", target = "nbrJourConge")
    @Mapping(source = "grades", target = "grades")
    @Mapping(source = "groupes", target = "groupes")
    @Mapping(source = "categories", target = "categories")
    @Mapping(source = "daysOff",target = "daysOff")
    EntrepriseWithoutContactsDTO entrepriseToEntrepriseWithoutContactsDTO(Entreprise entreprise);
}
