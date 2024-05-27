package com.PFE.RH.Controllers;

import com.PFE.RH.Services.SalaryGenerationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Map;

@RestController
public class SalaryGenerationController {

    private final SalaryGenerationService salaryGenerationService;

    public SalaryGenerationController(SalaryGenerationService salaryGenerationService) {
        this.salaryGenerationService = salaryGenerationService;
    }

    @PostMapping("/generate-salary")
    public void generateSalaryForContact(@RequestBody Map<String, Object> payload) {
        System.out.println("Received payload: " + payload.toString()); // Log the entire payload

        Long contactId = payload.get("contactId") != null ? Long.parseLong(payload.get("contactId").toString()) : null;
        Integer year = payload.get("year") != null ? Integer.parseInt(payload.get("year").toString()) : null;
        Integer month = payload.get("month") != null ? Integer.parseInt(payload.get("month").toString()) : null;
        String filePath = "C:\\Users\\Amen\\Desktop\\"; // Default file path, modify as needed

        if (contactId == null || year == null || month == null) {
            System.out.println("ContactId, year, or month is null.");
            // Handle the case where any of the required fields are null
            // For example, return an error response or log a message
            // You can also throw an exception if needed
            return;
        }

        salaryGenerationService.generateSalaryForContactById(contactId, year, month, filePath);
    }


    @GetMapping("/generate-salary-all")
    public void generateSalaryForAllContacts(@RequestParam int year, @RequestParam int month) {
        salaryGenerationService.generateSalaryForAllContacts(year, month);
    }

    @Scheduled(cron = "0 0 0 L * ?") // Run at 00:00:00 on the last day of every month
    public void generateSalaryForAllContactsScheduled() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        salaryGenerationService.generateSalaryForAllContacts(year, month);
    }
    private double round(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(df.format(value));
    }

}
