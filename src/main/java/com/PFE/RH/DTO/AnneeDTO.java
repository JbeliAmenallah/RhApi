package com.PFE.RH.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Set;

@Data
public class AnneeDTO {
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy") // Specify the French date format
    private String dateDebutExercice;
    private String libele;
    private Set<JourFerieWithoutAnneeDTO> jourFerieDTOs; // Include this field
}
