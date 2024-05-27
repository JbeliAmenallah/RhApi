package com.PFE.RH.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ContactDTO {
    private Long contactId;



    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Name must contain only letters and spaces")
    private String name;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username must contain only letters, numbers, and underscores")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private Integer soldeConge = 0;  // Initialize to 0

    @NotBlank(message = "Location is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Location must contain only letters, numbers, and spaces")
    private String location;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp="\\d{8}", message="Phone number must be 8 digits")
    private String phone;

    @NotBlank(message = "Fax is required")
    @Pattern(regexp="^\\+(?:[0-9] ?){6,14}[0-9]$", message="Fax must be in international format")
    private String fax;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,20}$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;

    @NotBlank(message = "Roles are required")
    @Pattern(regexp = "^(admin|user)$", message = "Roles must be 'admin' or 'user'")
    private String roles;
    private Integer nbEnfant;


    @NotBlank(message = "Regime is required")
    private String regime;

    private boolean chefDefamille;

    @NotNull(message = "Base Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base Salary must be greater than 0")
    private Double salaireDeBASE;

    @NotBlank(message = "Account Number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Account Number must be a 10-digit number")
    private String numCompte;

    @NotBlank(message = "Payment Method is required")
    private String modeDePaiement;

    @NotNull(message = "Recruitment Date is required")
    private LocalDate dateRecrutemnt;

    private List<EnfantWithoutContactIdDTO> enfants;
    private List<AbsenceWithHiddenContactIdDTO> absences;
    private List<PrimeWithoutTypeAndContactDTO> primes;
    private List<AutorisationWithoutContactDTO> autorisations;
    private List<CongeWithHiddenContactIdDTO> conges;
    private List<CotisationWithHiddenContactIdDTO> cotisations;
    private EntrepriseWithoutContactsDTO entreprise;
    private CategoryDTO category;
    private GradeDTO grade;
    private GroupeDTO groupe;
}
