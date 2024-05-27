package com.PFE.RH.DTO;

import lombok.Data;

@Data
public class CotisationDTO {
    private Long cotisationId;
    private String libele;
    private int annee;
    private double taux;
    private Long contactId; // Assuming you want to include contactId in DTO

    // Getters and setters
    // Omitted for brevity
}
