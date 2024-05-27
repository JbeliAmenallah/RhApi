package com.PFE.RH.DTO;

import lombok.Data;

@Data
public class TypePrimeDTO {
    private Long typePrimeId;
    private String code;
    private String libele;
    private Boolean cnss;
    private Boolean impo;
    private Double montant;
    private String type;
    private Boolean abasedesalaire;
    private Boolean obligatoire;

    public Boolean isCnss() {
        return cnss;
    }
    public Boolean isImpo() {
        return impo;
    }
}
