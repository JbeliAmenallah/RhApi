package com.PFE.RH.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@Data
public class AbsenceDTO {
    private Long absenceId;
    private Long contactId;

    @NotNull(message = "La date de début d'absence ne peut pas être vide")
    @PastOrPresent(message = "La date de début d'absence doit être dans le présent ou le passe ")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateDebutAbsence;

    @NotNull(message = "La date de fin d'absence ne peut pas être vide")
    @Future(message = "La date de fin d'absence doit être dans le futur")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateFinAbsence;

    @NotBlank(message = "La raison de l'absence ne peut pas être vide")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "La raison de l'absence doit contenir uniquement des caractères alphanumériques")
    private String reason;

    @JsonIgnore
    private String message; // Add this field for messages to use on feedbacks

    private boolean justified;


    // Method to generate the message
    public String generateMessage(String contactName) {
        return contactName + " était absent de " + dateDebutAbsence + " à " + dateFinAbsence + " en raison de " + reason + ".";
    }
}
