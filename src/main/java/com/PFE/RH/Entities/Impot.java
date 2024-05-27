package com.PFE.RH.Entities;

import com.PFE.RH.DTO.AnneeWithoutJourFerieDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
public class Impot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libele;
    private double taux;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_id")
    private Annee annee;
    // Constructors, getters, setters
}
