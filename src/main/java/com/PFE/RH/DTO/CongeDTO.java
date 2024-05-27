package com.PFE.RH.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CongeDTO {

    private Long congeId;

    @NotNull
    @PastOrPresent(message = "End date must be in the present or future")
    @Column(nullable = false)
    private LocalDate startDate;


    @NotNull
    @FutureOrPresent(message = "End date must be in the present or future")
    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    @NotBlank(message = "State cannot be blank")
    @Size(max = 50, message = "State must be less than or equal to 50 characters")
    private String state;
    private Long contactId; // Include contactId

    // Getters and setters
}
