package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.AbsenceDTO;
import com.PFE.RH.Entities.Absence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AbsenceMapper {

    @Mapping(source = "absenceId", target = "absenceId")
    @Mapping(source = "contact.contactId", target = "contactId")
    @Mapping(source = "dateDebutAbsence", target = "dateDebutAbsence")
    @Mapping(source = "dateFinAbsence", target = "dateFinAbsence")
    @Mapping(source = "reason", target = "reason")
    @Mapping(source = "justified",target="justified")
    AbsenceDTO absenceToAbsenceDTO(Absence absence);

    @Mapping(source = "absenceDTO.absenceId", target = "absenceId")
    @Mapping(source = "absenceDTO.contactId", target = "contact.contactId")
    @Mapping(source = "absenceDTO.dateDebutAbsence", target = "dateDebutAbsence")
    @Mapping(source = "absenceDTO.dateFinAbsence", target = "dateFinAbsence")
    @Mapping(source = "absenceDTO.reason", target = "reason")
    @Mapping(source = "absenceDTO.justified",target = "justified")
    Absence absenceDTOToAbsence(AbsenceDTO absenceDTO);
}
