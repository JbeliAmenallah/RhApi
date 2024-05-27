package com.PFE.RH.DTO;


import lombok.Data;

@Data
    public class DeductionDTO {
    private Long id;
    private String libelle;
    private String description;
    private String etat;
    private String typecalcul;
    private Long valeur;
    private Long anneeId; // New field for storing the ID of the associated Annee entity

    // Getters and setters (Omitted for brevity)
}
