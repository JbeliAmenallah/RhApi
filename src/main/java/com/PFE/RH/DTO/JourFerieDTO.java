package com.PFE.RH.DTO;

import lombok.Data;
import jakarta.validation.constraints.*;


@Data
public class JourFerieDTO {
    private Long id;
    @Min(value = 1, message = "Jour must be between 1 and 31")
    @Max(value = 31, message = "Jour must be between 1 and 31")
    private int jour;

    @Min(value = 1, message = "Mois must be between 1 and 12")
    @Max(value = 12, message = "Mois must be between 1 and 12")
    private int mois;

    @NotBlank(message = "Libele is required")
    private String libele;

    @NotNull(message = "AnneeId is required")
    private Long anneeId; // To hold the ID of associated Annee
}
