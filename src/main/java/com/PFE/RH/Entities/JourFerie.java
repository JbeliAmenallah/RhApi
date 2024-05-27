package com.PFE.RH.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class    JourFerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int jour;
    private int mois;
    private String libele;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Annee annee;
}
