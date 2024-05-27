package com.PFE.RH.DTO;



import lombok.Data;

import java.time.LocalDate;

@Data
public class AbsenceWithHiddenContactIdDTO {
    private Long absenceId;
    private LocalDate dateDebutAbsence;
    private LocalDate dateFinAbsence;
    private String reason;
    private boolean justified;
    // Getters and setters
    // Omitted for brevity

    // Method to generate the message
}

