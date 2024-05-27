package com.PFE.RH.Services;

import com.PFE.RH.Entities.*;
import com.PFE.RH.Repositories.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryGenerationService {

    private final ContactRepository contactRepository;
    private final FicheDePaieRepository ficheDePaieRepository;
    private final FinanceConfigurationRepository financeConfigurationRepository;
    private final PrimeRepository primeRepository;
    private final TemplateEngine templateEngine;
    private final JourFerieRepository jourFerieRepository;

    private final AnneeRepository anneeRepository;

    public SalaryGenerationService(ContactRepository contactRepository, FicheDePaieRepository ficheDePaieRepository,
                                   FinanceConfigurationRepository financeConfigurationRepository,
                                   PrimeRepository primeRepository, TemplateEngine templateEngine,JourFerieRepository jourFerieRepository,
                                   AnneeRepository anneeRepository) {
        this.contactRepository = contactRepository;
        this.ficheDePaieRepository = ficheDePaieRepository;
        this.financeConfigurationRepository = financeConfigurationRepository;
        this.primeRepository = primeRepository;
        this.templateEngine = templateEngine;
        this.jourFerieRepository=jourFerieRepository;
        this.anneeRepository=anneeRepository;
    }

    public void generateSalaryForContactById(Long contactId, int year, int month, String filePath) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found with ID: " + contactId));

        FicheDePaie ficheDePaie = new FicheDePaie();
        ficheDePaie.setContact(contact);
        ficheDePaie.setYear(year);
        ficheDePaie.setMonth(month);

        int soldeConge = contact.getSoldeConge();
        ficheDePaie.setNbrconge(soldeConge);
        System.out.println("Calculated soldeConge: " + soldeConge);

        FinanceConfiguration financeConfiguration = financeConfigurationRepository.findByAnnee_DateDebutExerciceStartingWith(String.valueOf(year))
                .orElseThrow(() -> new RuntimeException("Finance Configuration not found for year: " + year));

        // Log the finance configuration
        System.out.println("Finance Configuration: " + financeConfiguration);
        System.out.println("ID: " + financeConfiguration.getId());
        System.out.println("CNSS: " + financeConfiguration.getCnss());
        System.out.println("CSS1: " + financeConfiguration.getCss1());

        // Fetch the year ID directly
        String yearAsString = String.valueOf(year);
        Long yearId = anneeRepository.findIdByLibele(yearAsString)
                .orElseThrow(() -> new RuntimeException("Year ID not found for year: " + year));


    // Now use the year ID to query primes
        List<Prime> primes = primeRepository.findByContactContactIdAndYear(contactId, yearId);
        double sommePrimes = primes.stream()
                .mapToDouble(Prime::getMontant)
                .sum();
        ficheDePaie.setPrime(sommePrimes);
        System.out.println("Calculated sommePrimes: " + sommePrimes);


        double deductions = calculateDeductions(contact);
        System.out.println("Calculated deductions: " + deductions);

        double salaireBase = contact.getSalaireDeBASE();
        System.out.println("Salaire Base: " + salaireBase);

        double salaireBrute = salaireBase + sommePrimes;
        System.out.println("Salaire Brute: " + salaireBrute);

        double salaireImposable = salaireBrute; // This seems incorrect in the original code; it should reflect taxable income calculation.
        System.out.println("Salaire Imposable: " + salaireImposable);

        double x1 = salaireImposable * 0.9;
        double x2 = x1 * 12;
        double x3 = x2 - deductions;
        System.out.println("Intermediate Calculation x1: " + x1);
        System.out.println("Intermediate Calculation x2: " + x2);
        System.out.println("Intermediate Calculation x3: " + x3);

        double resulterpp = 0;
        double resultcss = 0;

        if (x3 >= 0 && x3 <= 5000) {
            resulterpp = 0;
            resultcss = x3 * 0;
        } else if (x3 >= 5000 && x3 <= 20000) {
            resulterpp = (x3 - 5000) * 0.26;
            resultcss = (x3 - 5000) * 0.26;
        } else if (x3 >= 20000 && x3 <= 30000) {
            resulterpp = (x3 - 20000) * 0.28 + 15000 * 0.26;
            resultcss = (x3 - 20000) * 0.285 + 15000 * 0.265;
        } else if (x3 >= 30000 && x3 <= 50000) {
            resulterpp = (x3 - 30000) * 0.32 + 10000 * 0.28 + 15000 * 0.26;
            resultcss = (x3 - 30000) * 0.325 + 10000 * 0.285 + 15000 * 0.265;
        } else if (x3 > 50000) {
            resulterpp = (x3 - 50000) * 0.35 + 20000 * 0.32 + 10000 * 0.28 + 15000 * 0.26;
            resultcss = (x3 - 50000) * 0.355 + 20000 * 0.325 + 10000 * 0.285 + 15000 * 0.265;
        }
        System.out.println("Calculated resulterpp: " + resulterpp);
        System.out.println("Calculated resultcss: " + resultcss);

        double erpp = resulterpp / 12;
        double css = (resultcss - resulterpp) / 12;
        double salaireNet = salaireImposable - erpp - css;

        System.out.println("Final Calculations - erpp: " + erpp + ", css: " + css + ", salaireNet: " + salaireNet);

        ficheDePaie.setSalaireNet(round(salaireNet));
        ficheDePaie.setCss(round(css));
        ficheDePaie.setErpp(round(erpp));

        // Log FicheDePaie details before saving
        System.out.println("FicheDePaie: " + ficheDePaie);

        System.out.println("Final Calculations - erpp: " + erpp + ", css: " + css + ", salaireNet: " + salaireNet);

        // Save FicheDePaie
        ficheDePaieRepository.save(ficheDePaie);

        // Update Contact details
        contact.setSalaireDeBASE(round(salaireBase));
        contact.setSoldeConge(soldeConge);

        byte[] pdfContent = generatePayslipPDF(contact, year, month, salaireBrute, sommePrimes, salaireImposable, salaireNet, css, erpp, soldeConge);
         filePath="C:\\Users\\Amen\\Downloads\\fiche de paie"+contact.getName() + "_fiche_de_paie.pdf";
        savePayslipPDFToFile(pdfContent, filePath);
    }


    private double round(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(value));
    }

    private byte[] generatePayslipPDF(Contact contact, int year, int month, double salaireBrute,
                                      double sommePrimes, double salaireImposable, double salaireNet,
                                      double css, double erpp, int soldeConge) {
        try {
            Context context = new Context();
            context.setVariable("contact", contact);
            context.setVariable("year", year);
            context.setVariable("month", month);
            context.setVariable("salaireBrute", round(salaireBrute));
            context.setVariable("sommePrimes", round(sommePrimes));
            context.setVariable("salaireImposable", round(salaireImposable));
            context.setVariable("css", round(css));
            context.setVariable("erpp", round(erpp));
            context.setVariable("salaireNet", round(salaireNet));
            context.setVariable("soldeConge", soldeConge);

            String html = templateEngine.process("payslip-template", context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);

            // Get the absolute path to the resources folder
            String resourceFolder = getClass().getClassLoader().getResource("Templates/").toString();
            // Remove the "file:" prefix and the "Templates/" part from the URL
            String absolutePath = resourceFolder.replace("file:", "").replace("Templates/", "");
            // Set the base URL
            renderer.getSharedContext().setBaseURL(absolutePath);

            // Set up PDF document
            // Set up PDF document
            // Set up PDF document
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            // Here you would add content to the Document
            Paragraph paragraph = new Paragraph("Fiche de Paie");
            document.add(paragraph);

            // Add more content as needed...

            // Close the Document
            document.close();

            renderer.layout();
            renderer.createPDF(outputStream, true);  // Pass the outputStream here

            return outputStream.toByteArray();
        } catch (DocumentException | com.lowagie.text.DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }




    private void savePayslipPDFToFile(byte[] pdfContent, String filePath) {
        if (pdfContent != null) {
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(pdfContent);
                System.out.println("Fiche de Paie PDF saved to: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to generate Fiche de Paie PDF.");
        }
    }

    private int calculateSoldeConge(Long contactId, int year, int month) {
        Optional<FicheDePaie> lastFiche = ficheDePaieRepository.findTopByContactContactIdOrderByYearDescMonthDesc(contactId);
        if (lastFiche.isPresent()) {
            FicheDePaie lastFicheDePaie = lastFiche.get();
            int lastSoldeConge = lastFicheDePaie.getNbrconge();
            System.out.println("last solde conge" + lastSoldeConge);
            int daysWorked = calculateDaysWorkedSinceLastFiche(lastFicheDePaie, year, month);
            System.out.println("days worked" + daysWorked);
            int soldeConge = lastSoldeConge + (daysWorked / 30) * 2;
            System.out.println("solde conge from last fiche" + soldeConge);
            return soldeConge;
        } else {
            // If no previous payslip, calculate based on recruitment date
            Contact contact = contactRepository.findById(contactId)
                    .orElseThrow(() -> new RuntimeException("Contact not found with ID: " + contactId));
            int daysSinceRecruitment = calculateDaysSinceRecruitment(contact.getDateRecrutemnt(),month);
            int soldeConge = (daysSinceRecruitment / 30) * 2;
            System.out.println("days" + daysSinceRecruitment);
            return soldeConge;
        }
    }

    private int calculateDaysWorkedSinceLastFiche(FicheDePaie lastFicheDePaie, int year, int month) {
        LocalDate lastFicheDate = LocalDate.of(lastFicheDePaie.getYear(), lastFicheDePaie.getMonth(), 1);
        LocalDate currentDate = LocalDate.of(year, month, 1);
        Period period = Period.between(lastFicheDate, currentDate);
        int totalDays = period.getYears() * 365 + period.getMonths() * 30 + period.getDays();
        // Fetch all JourFerie for the given month
        List<JourFerie> jourFeries = jourFerieRepository.findByMois(month);

        // Subtract the days that are public holidays
        for (JourFerie jourFerie : jourFeries) {
            // Check if the JourFerie is on a Sunday
            LocalDate jourFerieDate = LocalDate.of(currentDate.getYear(), month, jourFerie.getJour()); // Use current year
            DayOfWeek dayOfWeek = jourFerieDate.getDayOfWeek();

            // If JourFerie is Sunday (DayOfWeek.SUNDAY), subtract one day
            if (dayOfWeek != DayOfWeek.SUNDAY) {
                totalDays--;
            }

            // Subtract the JourFerie day
            totalDays -= jourFerie.getJour();
        }
        return totalDays;
    }

    private int calculateDaysSinceRecruitment(LocalDate recruitmentDate,int month) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(recruitmentDate, currentDate);
        int totalDays = period.getYears() * 365 + period.getMonths() * 30 + period.getDays();
        List<JourFerie> jourFeries = jourFerieRepository.findByMois(month);

        // Subtract the days that are public holidays
        for (JourFerie jourFerie : jourFeries) {
            // Check if the JourFerie is on a Sunday
            LocalDate jourFerieDate = LocalDate.of(currentDate.getYear(), month, jourFerie.getJour()); // Use current year
            DayOfWeek dayOfWeek = jourFerieDate.getDayOfWeek();

            // If JourFerie is Sunday (DayOfWeek.SUNDAY), subtract one more day
            if (dayOfWeek != DayOfWeek.SUNDAY) {
                totalDays--;
            }

            // Subtract the JourFerie day
            totalDays -= jourFerie.getJour();
        }
        return totalDays;
    }

    public void generateSalaryForAllContacts(int year, int month) {
        List<Contact> contacts = contactRepository.findAll();
        for (Contact contact : contacts) {
            // Generate dynamic file name based on contact information
            String fileName = "C:\\Users\\Amen\\Downloads\\fiche de paie"+contact.getName() + "_fiche_de_paie.pdf";
            generateSalaryForContactById(contact.getContactId(), year, month, fileName);
        }
    }
    private double calculateDeductions(Contact contact) {
        double totalDeductions = 0;

        // Deduction for head of the family
        if (contact.isChefDefamille()) {
            // Apply a deduction of 150 to 300 dinars (you can adjust the range as needed)
            totalDeductions += 150; // For example, you can start with 150 dinars
        }

        // Additional deduction for the first four children
        int nbEnfant = contact.getNbEnfant();
        List<Enfant> enfants = contact.getEnfants();
        if (nbEnfant > 0 && enfants != null) {
            int additionalDeductionCount = Math.min(nbEnfant, 4); // Consider up to the first four children
            for (int i = 0; i < additionalDeductionCount; i++) {
                // Apply a deduction of 100 dinars per child
                totalDeductions += 100;
            }
            System.out.println("total deductions1 : "+ totalDeductions);
        }

        // Deduction per disabled child
        if (enfants != null) {
            for (Enfant enfant : enfants) {
                if (enfant.isDisabled()) {
                    // Apply a deduction of 1,200 to 2,000 dinars per disabled child
                    // You can adjust the deduction amount based on the child's age or other factors
                    totalDeductions += 1200; // For example, start with a fixed amount
                }
            }
            System.out.println("total deductions2 : "+ totalDeductions);
        }

        return totalDeductions;
    }

}
