package com.PFE.RH.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "absences")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long absenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") // French date and time format
    @NotNull(message = "La date de début d'absence ne peut pas être vide")
    @PastOrPresent(message = "La date de début d'absence doit être dans le présent ou le passe ")
    private LocalDateTime dateDebutAbsence;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") // French date and time format
    @NotNull(message = "La date de fin d'absence ne peut pas être vide")
    @Future(message = "La date de fin d'absence doit être dans le futur")
    private LocalDateTime dateFinAbsence;

    @NotBlank(message = "La raison de l'absence ne peut pas être vide")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "La raison de l'absence doit contenir uniquement des caractères alphanumériques")
    private String reason;

    private boolean justified;
    // Getters and setters
    // Omitted for brevity
}
