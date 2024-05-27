package com.PFE.RH.Repositories;

import com.PFE.RH.Entities.Contact;
import com.PFE.RH.Entities.FicheDePaie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FicheDePaieRepository extends JpaRepository<FicheDePaie, Long> {


    List<FicheDePaie> findByContactContactId(Long contactId);



    Optional<FicheDePaie> findTopByContactContactIdOrderByYearDescMonthDesc(Long contactId);
}
