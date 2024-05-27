package com.PFE.RH.Repositories;

import com.PFE.RH.Entities.Annee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnneeRepository extends JpaRepository<Annee,Long> {
   // Optional<Annee> findByLibele(String libele);



    @Query("SELECT a.id FROM Annee a WHERE a.libele = :libele")
    Optional<Long> findIdByLibele(@Param("libele") String libele);

}
