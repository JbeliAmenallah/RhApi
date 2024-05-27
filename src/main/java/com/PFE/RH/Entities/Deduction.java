package com.PFE.RH.Entities;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
@Table(name = "deduction")
public class Deduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private String description;
    private String etat;
    private String typecalcul;
    private Long valeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_id")
    private Annee annee;


    // Getters and Setters (Omitted for brevity)
}
