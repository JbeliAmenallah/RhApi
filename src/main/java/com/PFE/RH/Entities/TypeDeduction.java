package com.PFE.RH.Entities;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
@Table(name = "type_deduction")
public class TypeDeduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ref;
    private String designation;
    private String commune;
    private double montant;
    private double pourcentage;

    // Getters and Setters (Omitted for brevity)
}
