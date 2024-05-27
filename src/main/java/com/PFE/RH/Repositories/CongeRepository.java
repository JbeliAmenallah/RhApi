package com.PFE.RH.Repositories;

import com.PFE.RH.Entities.Conge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongeRepository extends JpaRepository<Conge, Long> {
    List<Conge> findByContactContactId(Long contactId);
    @Query("SELECT c FROM Conge c WHERE YEAR(c.startDate) = :year")
    List<Conge> findAllByYear(@Param("year") int year);

    List<Conge> findByContactUsername(String username);
}
