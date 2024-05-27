package com.PFE.RH.Services;

import com.PFE.RH.DTO.KPIDTO;
import com.PFE.RH.Entities.Absence;
import com.PFE.RH.Entities.Contact;
import com.PFE.RH.Repositories.ContactRepository;
import com.PFE.RH.Repositories.KPIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class KPIService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private KPIRepository kpiRepository;

    public List<KPIDTO> calculateKPIsForContact(Long contactId) {
        Optional<Contact> optionalContact = contactRepository.findById(contactId);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            List<KPIDTO> kpis = new ArrayList<>();

            // Calculate average tenure
            double averageTenure = calculateAverageTenureInYears(contact);
            kpis.add(new KPIDTO(null, contactId, "Average Tenure (Years)", averageTenure, LocalDate.now()));

            // Calculate total absence days
            int totalAbsenceDays = getTotalAbsenceDays(contact);
            kpis.add(new KPIDTO(null, contactId, "Total Absence Days", totalAbsenceDays, LocalDate.now()));

            // Calculate family responsibilities
            double familyResponsibilities = calculateFamilyResponsibilities(contact);
            kpis.add(new KPIDTO(null, contactId, "Family Responsibilities", familyResponsibilities, LocalDate.now()));

            // Calculate performance
            double performance = calculatePerformance(contact);
            kpis.add(new KPIDTO(null, contactId, "Performance", performance, LocalDate.now()));

            return kpis;
        } else {
            throw new NoSuchElementException("Contact not found with ID: " + contactId);
        }
    }


    // Helper method to calculate average tenure
    private double calculateAverageTenureInYears(Contact contact) {
        LocalDate currentDate = LocalDate.now();
        LocalDate recruitmentDate = contact.getDateRecrutemnt();
        long totalMonths = ChronoUnit.MONTHS.between(recruitmentDate, currentDate);
        return totalMonths / 12.0; // Convert months to years
    }

    // Helper method to calculate total absence days
    private int getTotalAbsenceDays(Contact contact) {
        List<Absence> absences = contact.getAbsences();
        int totalAbsenceDays = 0;
        for (Absence absence : absences) {
            totalAbsenceDays += calculateNumberOfDays(absence.getDateDebutAbsence(), absence.getDateFinAbsence());
        }
        return totalAbsenceDays;
    }

    // Helper method to calculate the number of days between two dates
    private int calculateNumberOfDays(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();
        return (int) ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)); // Adding 1 to include the end date
    }

    // Helper method to calculate family responsibilities (example: chef de famille = 1.1, not = 1.0, based on number of children)
    private double calculateFamilyResponsibilities(Contact contact) {
        boolean isChefDeFamille = contact.isChefDefamille();
        int numberOfChildren = contact.getEnfants().size();
        double coefficient = isChefDeFamille ? 1.1 : 1.0;
        return coefficient + (numberOfChildren * 0.05); // Example: each child adds 0.05
    }

    // Helper method to calculate performance (example: total bonuses / base salary)
    private double calculatePerformance(Contact contact) {
        double totalPrimes = contact.getPrimes().stream().mapToDouble(prime -> prime.getMontant()).sum();
        double baseSalary = contact.getSalaireDeBASE();
        return totalPrimes / baseSalary;
    }

}
