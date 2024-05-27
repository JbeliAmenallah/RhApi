package com.PFE.RH.DTO;

import lombok.Data;

@Data
public class EnfantDTO {
    private Long id;
    private String name;
    private String familyName;
    private int age;
    private boolean disabled;
    private boolean bourse;
    private String educationGrade;
    private Long contactId; // Only store the contactId
}
