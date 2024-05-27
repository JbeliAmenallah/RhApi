package com.PFE.RH.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class KPI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kpi_id")
    private Long kpiId;


    private Long contactId;

    private String kpiName;

    private double value;

    private LocalDate date;

    // Constructors, getters, setters, etc.
}
