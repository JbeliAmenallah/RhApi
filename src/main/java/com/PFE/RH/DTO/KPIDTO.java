package com.PFE.RH.DTO;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KPIDTO {
    private Long kpiId;
    private Long contactId;
    private String kpiName;
    private double value;
    private LocalDate date;
}
