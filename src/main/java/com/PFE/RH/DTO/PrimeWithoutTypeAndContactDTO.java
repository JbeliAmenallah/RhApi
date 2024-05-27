package com.PFE.RH.DTO;

import lombok.Data;

@Data
public class PrimeWithoutTypeAndContactDTO {
    private Long primeId;
    private int year;
    private int month;
    private Double montant;
    private String motif;

    // Getters and setters
    // Omitted for brevity
}
