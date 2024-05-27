package com.PFE.RH.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "finance_configuration" ,uniqueConstraints = @UniqueConstraint(columnNames = {"annee_id"}))
public class FinanceConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cnss")
    private double cnss;

    @Column(name = "css1")
    private double css1;

    @Column(name = "css2")
    private double css2;

    @Column(name = "css3")
    private double css3;

    @Column(name = "css4")
    private double css4;

    @Column(name = "css5")
    private double css5;

    @Column(name = "tva")
    private double tva;

    @Column(name = "deduction")
    private double deduction;

    @Column(name = "irpp1")
    private double irpp1;

    @Column(name = "irpp2")
    private double irpp2;

    @Column(name = "irpp3")
    private double irpp3;

    @Column(name = "irpp4")
    private double irpp4;

    @Column(name = "irpp5")
    private double irpp5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_id") // Foreign key column
    private Annee annee;

    // Getters and setters
    // Constructors, etc.
}
