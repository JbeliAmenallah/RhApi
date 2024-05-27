package com.PFE.RH.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Annee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy") // Specify the French date format
    private String dateDebutExercice;
    private String libele;

    @OneToMany(mappedBy = "annee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<JourFerie> jourFeries = new HashSet<>();


    // Constructors, getters, setters
}
