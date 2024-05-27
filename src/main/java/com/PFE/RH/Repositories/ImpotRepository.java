package com.PFE.RH.Repositories;

import com.PFE.RH.DTO.ImpotProjectionDTO;
import com.PFE.RH.Entities.Impot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImpotRepository extends JpaRepository<Impot, Long> {
    Optional<ImpotProjectionDTO> findProjectedById(Long impotId);

}
