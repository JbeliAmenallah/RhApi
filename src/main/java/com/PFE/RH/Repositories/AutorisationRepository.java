package com.PFE.RH.Repositories;

import com.PFE.RH.Entities.Autorisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorisationRepository extends JpaRepository<Autorisation,Long> {
    List<Autorisation> findByContact_ContactId(Long contactId);
}
