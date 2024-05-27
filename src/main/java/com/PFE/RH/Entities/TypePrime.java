package com.PFE.RH.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
public class TypePrime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typePrimeId;

    private String code;
    private String libele;
    private Double montant;
    private String type;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean cnss;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean impo;


    @Column(columnDefinition = "BOOLEAN")
    private Boolean abasedesalaire;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean obligatoire;

    // Getters and setters
    // Omitted for brevity
}
