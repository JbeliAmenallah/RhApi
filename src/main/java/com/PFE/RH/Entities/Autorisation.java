package com.PFE.RH.Entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "autorisation")
public class Autorisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autorisationId;
    @NotNull(message = "Start date (dateDebut) is required")
    @PastOrPresent(message = "Start date (dateDebut) must be in the past or present")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") // French date and time format
    private LocalDateTime dateDebut;

    @NotNull(message = "End date (dateFin) is required")
    @FutureOrPresent(message = "End date (dateFin) must be in the future or present")
    // @DateTimeFormat(pattern = "dd/MM/yyyy -- HH:mm")
    private LocalDateTime dateFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @NotBlank(message = "State is required")
    @Size(min = 1, max = 50, message = "State must be between 1 and 50 characters")
    private String state;

    // Getters and Setters (Omitted for brevity)
}
