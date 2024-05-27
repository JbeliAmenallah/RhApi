package com.PFE.RH.Repositories;

import com.PFE.RH.Entities.Prime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrimeRepository extends JpaRepository<Prime, Long> {
    List<Prime> findByContactContactId(Long contactId);
    List<Prime> findByContactContactIdAndYear(Long contactId, int year);

    List<Prime> findByContactContactIdAndYear(Long contactId, Long year);
}
