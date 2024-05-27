package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.*;
import com.PFE.RH.Entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {EntrepriseWithoutContactsMapper.class, ImpotProjectionMapper.class})
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    @Mapping(source = "contact.absences", target = "absences")
    @Mapping(source = "contact.primes", target = "primes")
    @Mapping(source = "contact.autorisations", target = "autorisations")
    @Mapping(source = "contact.contactId", target = "contactId")
    @Mapping(source = "contact.name", target = "name")
    @Mapping(source = "contact.username", target = "username")
    @Mapping(source = "contact.email", target = "email")
    @Mapping(source = "contact.location", target = "location")
    @Mapping(source = "contact.phone", target = "phone")
    @Mapping(source = "contact.conges", target = "conges")
    //@Mapping(source = "contact.cotisations", target = "cotisations")
    @Mapping(source = "contact.enfants",target="enfants")
    @Mapping(source = "contact.soldeConge",target = "soldeConge")
    @Mapping(source = "contact.entreprise", target = "entreprise")
    @Mapping(source = "contact.fax", target = "fax") // Mapping for fax
    @Mapping(source = "contact.password", target = "password") // Mapping for password
    @Mapping(source = "contact.roles", target = "roles") // Mapping for roles
    @Mapping(source = "contact.nbEnfant", target = "nbEnfant") // Mapping for nbEnfant
    @Mapping(source = "contact.regime", target = "regime") // Mapping for regime
    @Mapping(source = "contact.chefDefamille", target = "chefDefamille") // Mapping for chefDefamille
    @Mapping(source = "contact.salaireDeBASE", target = "salaireDeBASE") // Mapping for salaireDeBASE
    @Mapping(source = "contact.numCompte", target = "numCompte") // Mapping for numCompte
    @Mapping(source = "contact.modeDePaiement", target = "modeDePaiement") // Mapping for modeDePaiement
    @Mapping(source = "contact.dateRecrutemnt", target = "dateRecrutemnt")// Mapping for dateRecrutemnt
    @Mapping(source = "contact.category",target = "category")
    @Mapping(source = "contact.groupe",target = "groupe")
    @Mapping(source = "contact.grade",target = "grade")



    ContactDTO contactToContactDTO(Contact contact);

    @Mapping(target = "absences", ignore = true)
    @Mapping(target = "primes", ignore = true)
    @Mapping(target = "autorisations", ignore = true)
    @Mapping(target = "conges", ignore = true)
    @Mapping(target="enfants",ignore = true)
    //@Mapping(source = "cotisations", target = "cotisations")
    @Mapping(source = "fax", target = "fax") // Mapping for fax
    @Mapping(source = "password", target = "password") // Mapping for password
    @Mapping(source = "roles", target = "roles") // Mapping for roles
    @Mapping(source = "soldeConge",target = "soldeConge")
    @Mapping(source = "nbEnfant", target = "nbEnfant") // Mapping for nbEnfant
    @Mapping(source = "regime", target = "regime") // Mapping for regime
    @Mapping(source = "chefDefamille", target = "chefDefamille") // Mapping for chefDefamille
    @Mapping(source = "salaireDeBASE", target = "salaireDeBASE") // Mapping for salaireDeBASE
    @Mapping(source = "numCompte", target = "numCompte") // Mapping for numCompte
    @Mapping(source = "modeDePaiement", target = "modeDePaiement") // Mapping for modeDePaiement
    @Mapping(source = "dateRecrutemnt", target = "dateRecrutemnt") // Mapping for dateRecrutemnt
    Contact contactDTOToContact(ContactDTO contactDTO);

    @Mappings({
            @Mapping(source = "congeId", target = "congeId"),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate"),
            @Mapping(source = "state", target = "state"),
    })
    CongeWithHiddenContactIdDTO congeToCongeWithHiddenContactIdDTO(Conge conge);
}
