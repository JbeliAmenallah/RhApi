package com.PFE.RH.Repositories;

import com.PFE.RH.Entities.Absence;
import com.PFE.RH.Entities.Conge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByContactUsername(String username);
    List<Absence> findByContactContactId(Long contactId);
    List<Absence> findByDateDebutAbsenceBetween(LocalDateTime startDate, LocalDateTime endDate);

}
