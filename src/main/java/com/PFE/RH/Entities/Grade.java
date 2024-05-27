package com.PFE.RH.Entities;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grade_id;

    private String libele;

    // Constructors, getters, and setters
}
