package com.PFE.RH.Entities;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    private String libele;

    // Constructors, getters, and setters
}
