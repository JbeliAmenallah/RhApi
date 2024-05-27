package com.PFE.RH.Entities;

import lombok.Data;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entrepriseId;
    private String nom;
    private String matricule;
    private String siegesociale;
    private String raisonSociale;
    private String adresseDeSiege;
    private String matriculeFiscale;
    private String numCnss;
    private String regimeSalariale;
    private Integer nbrJourConge;
    @ElementCollection
    private List<String> daysOff = new ArrayList<>();
    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "entreprise_id")
    private List<Grade> grades = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "entreprise_id")
    private List<Groupe> groupes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "entreprise_id")
    private List<Category> categories = new ArrayList<>();
}
