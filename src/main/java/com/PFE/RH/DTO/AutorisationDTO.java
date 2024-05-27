package com.PFE.RH.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AutorisationDTO {

    private Long autorisationId;

    @NotNull(message = "Start date (dateDebut) is required")
    @PastOrPresent(message = "Start date (dateDebut) must be in the past or present")
    /*@DateTimeFormat(pattern = "dd/MM/yyyy -- HH:mm")*/
    private LocalDateTime dateDebut;

    @NotNull(message = "End date (dateFin) is required")
    @FutureOrPresent(message = "End date (dateFin) must be in the future or present")
   // @DateTimeFormat(pattern = "dd/MM/yyyy -- HH:mm")
    private LocalDateTime dateFin;

    @NotNull(message = "Contact ID is required")
    private Long contactId;

    @JsonIgnore
    private String contactName;

    @NotBlank(message = "State is required")
    @Size(min = 1, max = 50, message = "State must be between 1 and 50 characters")
    private String state;

    // Getters and Setters (Omitted for brevity)
}