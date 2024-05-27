package com.PFE.RH.Repositories;

import com.PFE.RH.Entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupeRepository extends JpaRepository<Groupe,Long> {
    List<Groupe> findByLibele(String libele);
}
