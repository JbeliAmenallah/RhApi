package com.PFE.RH.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "conge")
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long congeId;


    @NotNull
    @PastOrPresent(message = "End date must be in the present or future")
    @Column(nullable = false)
    private LocalDate startDate;


    @NotNull
    @FutureOrPresent(message = "End date must be in the present or future")
    @Column(nullable = false)
    private LocalDate endDate;

    @NotBlank
    @Column(nullable = false)
    @Size(max = 50)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    // Getters and Setters (omitted for brevity)
}
