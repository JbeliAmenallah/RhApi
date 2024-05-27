package com.PFE.RH.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
@Table(name = "enfants")
public class Enfant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String familyName;
    private int age;
    private boolean disabled;
    private boolean bourse;
    private String educationGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    @JsonIgnore
    private Contact contact;

    // Getters and setters
}
