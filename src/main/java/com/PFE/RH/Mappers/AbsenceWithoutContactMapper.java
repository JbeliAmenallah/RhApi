package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.AbsenceWithHiddenContactIdDTO;
import com.PFE.RH.Entities.Absence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface AbsenceWithoutContactMapper {

    AbsenceWithoutContactMapper INSTANCE = Mappers.getMapper(AbsenceWithoutContactMapper.class);

    @Mapping(source = "absenceId", target = "absenceId")
    @Mapping(source = "dateDebutAbsence", target = "dateDebutAbsence")
    @Mapping(source = "dateFinAbsence", target = "dateFinAbsence")
    @Mapping(source = "reason", target = "reason")
    @Mapping(source = "justified",target = "justified")
    AbsenceWithHiddenContactIdDTO absenceToAbsenceWithoutContactDTO(Absence absence);

    default LocalDate mapDate(Date date) {
        if (date != null) {
            Instant instant = Instant.ofEpochMilli(date.getTime());
            return instant.atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    default Date mapLocalDate(LocalDate localDate) {
        if (localDate != null) {
            return Date.valueOf(localDate);
        }
        return null;
    }
}
