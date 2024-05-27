package com.PFE.RH.Entities;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
public class FicheDePaie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double salaireNet;
    private double erpp;
    private double css;
    private double prime;
    private int year;
    private int month;
    private int nbrconge;
    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;


    @Override
    public String toString() {
        return "FicheDePaie{" +
                "id=" + id +
                ", css=" + css +
                ", erpp=" + erpp +
                ", month=" + month +
                ", nbrconge=" + nbrconge +
                ", prime=" + prime +
                ", salaireNet=" + salaireNet +
                ", year=" + year +
                '}';
    }

    // Constructors, Getters, and Setters (omitted for brevity)
}
