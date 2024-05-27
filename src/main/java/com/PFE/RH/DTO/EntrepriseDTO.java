package com.PFE.RH.DTO;

import lombok.Data;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class EntrepriseDTO {
    private Long entrepriseId;
    private String nom;
    private String matricule;
    private String siegesociale;
    private String raisonSociale;
    private String adresseDeSiege;
    private String matriculeFiscale;
    private String numCnss;
    private String regimeSalariale;
    private Integer nbrJourConge;
    private List<String> daysOff = new ArrayList<>();
    private List<ContactDTO> contacts = new ArrayList<>();
    private List<GradeDTO> grades = new ArrayList<>();
    private List<GroupeDTO> groupes = new ArrayList<>();
    private List<CategoryDTO> categories = new ArrayList<>();
}
