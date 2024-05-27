package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.PrimeDTO;
import com.PFE.RH.Entities.Prime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrimeMapper {

    @Mapping(source = "primeId", target = "primeId")
    @Mapping(source = "contact.contactId", target = "contactId")
    @Mapping(source = "montant", target = "montant")
    @Mapping(source = "year", target = "year")
    @Mapping(source = "typePrime.typePrimeId", target = "typePrimeId")
    @Mapping(source="category_id",target = "category_id")
    @Mapping(source="grade_id",target = "grade_id")
    @Mapping(source="groupe_id",target = "groupe_id")
    PrimeDTO primeToPrimeDTO(Prime prime);

    @Mapping(source = "primeDTO.primeId", target = "primeId")
    @Mapping(source = "primeDTO.contactId", target = "contact.contactId")
    @Mapping(source = "primeDTO.montant", target = "montant")
    @Mapping(source="primeDTO.category_id",target = "category_id")
    @Mapping(source="primeDTO.grade_id",target = "grade_id")
    @Mapping(source="primeDTO.groupe_id",target = "groupe_id")
    @Mapping(source = "primeDTO.year", target = "year")
    Prime primeDTOToPrime(PrimeDTO primeDTO);

}
