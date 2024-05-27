package com.PFE.RH.Repositories;
import java.util.List;

import com.PFE.RH.Entities.FinanceConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinanceConfigurationRepository extends JpaRepository<FinanceConfiguration, Long> {
    Optional<FinanceConfiguration> findByAnnee_DateDebutExerciceStartingWith(String s);
    // Remove the findByLibeleAndYear method

}

