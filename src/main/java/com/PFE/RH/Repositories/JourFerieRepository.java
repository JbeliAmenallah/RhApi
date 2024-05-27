package com.PFE.RH.Repositories;

import com.PFE.RH.Entities.JourFerie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourFerieRepository extends JpaRepository<JourFerie, Long> {
    List<JourFerie> findByMois(int month);
    // Add custom query methods if needed
}
