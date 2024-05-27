package com.PFE.RH.DTO;

import lombok.Data;

@Data
public class FicheDePaieDTO {
    private Long id;
    private Long contactId;
    private double salaireNet;
    private double erpp;
    private double css;
    private double prime;
    private int nbrconge;
    private int year;
    private int month;

    // Constructors, Getters, and Setters (omitted for brevity)
}
