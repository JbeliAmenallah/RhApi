package com.PFE.RH.DTO;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CongeWithHiddenContactIdDTO {
    private Long congeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String state;

}
