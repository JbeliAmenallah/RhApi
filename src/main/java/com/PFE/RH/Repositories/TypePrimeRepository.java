package com.PFE.RH.Repositories;

import com.PFE.RH.Entities.TypePrime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePrimeRepository extends JpaRepository<TypePrime, Long> {
    TypePrime findByLibele(String libele);

    TypePrime findByCode(String code);
}
