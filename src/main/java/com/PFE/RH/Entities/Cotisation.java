package com.PFE.RH.Entities;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "cotisation")
public class Cotisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cotisationId;

    private String libele;
    private int annee;
    private double taux;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    // Constructors, Getters and Setters (omitted for brevity)
}
