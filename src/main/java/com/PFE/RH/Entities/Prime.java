package com.PFE.RH.Entities;

import lombok.Data;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "prime")
public class Prime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long primeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    private int year;
    private int month;
    private Double montant;
    private String motif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_prime_id")
    private TypePrime typePrime;

    private Long category_id;
    private Long groupe_id;
    private Long grade_id;
    // Getters and setters
    // Omitted for brevity
}
