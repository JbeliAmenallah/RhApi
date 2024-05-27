package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.TypePrimeDTO;
import com.PFE.RH.Entities.TypePrime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TypePrimeMapper {

    TypePrimeMapper INSTANCE = Mappers.getMapper(TypePrimeMapper.class);

    @Mapping(source = "typePrimeId", target = "typePrimeId")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "libele", target = "libele")
    @Mapping(source = "cnss", target = "cnss")
    @Mapping(source = "impo", target = "impo")
    @Mapping(source = "montant", target = "montant")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "abasedesalaire", target = "abasedesalaire")
    @Mapping(source = "obligatoire", target = "obligatoire")
    TypePrimeDTO typePrimeToTypePrimeDTO(TypePrime typePrime);

    @Mapping(source = "typePrimeId", target = "typePrimeId")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "libele", target = "libele")
    @Mapping(source = "cnss", target = "cnss")
    @Mapping(source = "impo", target = "impo")
    @Mapping(source = "montant", target = "montant")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "abasedesalaire", target = "abasedesalaire")
    @Mapping(source = "obligatoire", target = "obligatoire")
    TypePrime typePrimeDTOToTypePrime(TypePrimeDTO typePrimeDTO);
}
