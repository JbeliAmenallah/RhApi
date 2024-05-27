package com.PFE.RH.DTO;

import lombok.Data;

@Data
public class CotisationWithHiddenContactIdDTO {
    private Long cotisationId;
    private String libele;
    private int annee;
    private double taux;
}
