package com.PFE.RH.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ImpotDTO {
    private Long id;
    private String libele;
    private double taux;
    @JsonIgnore
    private AnneeWithoutJourFerieDTO anneeWithoutJourFerieDTO; // Use AnneeWithoutJourFerieDTO
}
