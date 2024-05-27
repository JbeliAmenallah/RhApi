package com.PFE.RH.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AnneeWithoutJourFerieDTO {
    private Long id;
    private String dateDebutExercice;
    private String libele;
}
