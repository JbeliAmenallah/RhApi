package com.PFE.RH.DTO;

import lombok.Data;

@Data
public class TypeDeductionDTO {
    private Long id;
    private String ref;
    private String designation;
    private String commune;
    private double montant;
    private double pourcentage;
}
