package com.PFE.RH.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ImpotWithAnneeLibeleDTO {
    @JsonIgnore
    private Long id;
    private String libele;
    private double taux;
    private String anneeLibele; // Just the libele of AnneeDTO
}
