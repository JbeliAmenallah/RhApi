package com.PFE.RH.Services;

import com.PFE.RH.DTO.FinanceConfigurationDTO;
import com.PFE.RH.Entities.FinanceConfiguration;
import com.PFE.RH.Repositories.FinanceConfigurationRepository;
import com.PFE.RH.Repositories.AnneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class FinanceConfigurationService {

    private final FinanceConfigurationRepository financeConfigurationRepository;
    private final AnneeRepository anneeRepository;


    @Autowired
    public FinanceConfigurationService(FinanceConfigurationRepository financeConfigurationRepository,
                                       AnneeRepository anneeRepository) {
        this.financeConfigurationRepository = financeConfigurationRepository;
        this.anneeRepository = anneeRepository;
    }

    public List<FinanceConfigurationDTO> getAllFinanceConfigurations() {
        List<FinanceConfiguration> financeConfigurations = financeConfigurationRepository.findAll();
        return financeConfigurations.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public FinanceConfigurationDTO getFinanceConfigurationById(Long id) {
        FinanceConfiguration financeConfiguration = financeConfigurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finance Configuration not found with ID: " + id));
        return mapToDto(financeConfiguration);
    }

    public FinanceConfigurationDTO createFinanceConfiguration(FinanceConfigurationDTO financeConfigurationDTO) {
        FinanceConfiguration financeConfiguration = mapToEntity(financeConfigurationDTO);
        financeConfiguration = financeConfigurationRepository.save(financeConfiguration);
        System.out.println(financeConfigurationDTO.getCnss());

        return mapToDto(financeConfiguration);
    }

    public FinanceConfigurationDTO getFinanceConfigurationOfCurrentOrPreviousYear() {
        int currentYear = Year.now().getValue();

        // Try to retrieve the finance configuration for the current year
        Optional<FinanceConfiguration> currentYearConfig = financeConfigurationRepository.findByAnnee_DateDebutExerciceStartingWith(String.valueOf(currentYear));
        if (currentYearConfig.isPresent()) {
            // If a finance configuration for the current year exists, return its DTO
            return mapToDto(currentYearConfig.get());
        } else {
            // If not, try to retrieve the finance configuration for the previous year
            int previousYear = currentYear - 1;
            Optional<FinanceConfiguration> previousYearConfig = financeConfigurationRepository.findByAnnee_DateDebutExerciceStartingWith(String.valueOf(previousYear));
            if (previousYearConfig.isPresent()) {
                // If a finance configuration for the previous year exists, return its DTO
                return mapToDto(previousYearConfig.get());
            } else {
                // If neither the current nor the previous year has a finance configuration, return an error message
                throw new RuntimeException("No finance configuration found for the current or previous year.");
            }
        }
    }

    public FinanceConfigurationDTO updateFinanceConfiguration(Long id, FinanceConfigurationDTO financeConfigurationDTO) {
        FinanceConfiguration existingFinanceConfiguration = financeConfigurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finance Configuration not found with ID: " + id));

        mapToEntity(financeConfigurationDTO, existingFinanceConfiguration);

        FinanceConfiguration updatedFinanceConfiguration = financeConfigurationRepository.save(existingFinanceConfiguration);
        return mapToDto(updatedFinanceConfiguration);
    }

    public FinanceConfigurationDTO patchFinanceConfiguration(Long id, FinanceConfigurationDTO financeConfigurationDTO) {
        FinanceConfiguration existingFinanceConfiguration = financeConfigurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finance Configuration not found with ID: " + id));

        mapToEntity(financeConfigurationDTO, existingFinanceConfiguration);

        FinanceConfiguration patchedFinanceConfiguration = financeConfigurationRepository.save(existingFinanceConfiguration);
        return mapToDto(patchedFinanceConfiguration);
    }

    public void deleteFinanceConfiguration(Long id) {
        financeConfigurationRepository.deleteById(id);
    }

    private FinanceConfigurationDTO mapToDto(FinanceConfiguration financeConfiguration) {
        FinanceConfigurationDTO dto = new FinanceConfigurationDTO();
        dto.setId(financeConfiguration.getId());
        dto.setCnss(financeConfiguration.getCnss());
        dto.setCss1(financeConfiguration.getCss1());
        dto.setCss2(financeConfiguration.getCss2());
        dto.setCss3(financeConfiguration.getCss3());
        dto.setCss4(financeConfiguration.getCss4());
        dto.setCss5(financeConfiguration.getCss5());
        dto.setTva(financeConfiguration.getTva());
        dto.setDeduction(financeConfiguration.getDeduction());
        dto.setIrpp1(financeConfiguration.getIrpp1());
        dto.setIrpp2(financeConfiguration.getIrpp2());
        dto.setIrpp3(financeConfiguration.getIrpp3());
        dto.setIrpp4(financeConfiguration.getIrpp4());
        dto.setIrpp5(financeConfiguration.getIrpp5());
        if (financeConfiguration.getAnnee() != null) {
            dto.setAnneeId(financeConfiguration.getAnnee().getId());
        }
        return dto;
    }

    private FinanceConfiguration mapToEntity(FinanceConfigurationDTO dto) {
        FinanceConfiguration financeConfiguration = new FinanceConfiguration();
        financeConfiguration.setCnss(dto.getCnss());
        financeConfiguration.setCss1(dto.getCss1());
        financeConfiguration.setCss2(dto.getCss2());
        financeConfiguration.setCss3(dto.getCss3());
        financeConfiguration.setCss4(dto.getCss4());
        financeConfiguration.setCss5(dto.getCss5());
        financeConfiguration.setTva(dto.getTva());
        financeConfiguration.setDeduction(dto.getDeduction());
        financeConfiguration.setIrpp1(dto.getIrpp1());
        financeConfiguration.setIrpp2(dto.getIrpp2());
        financeConfiguration.setIrpp3(dto.getIrpp3());
        financeConfiguration.setIrpp4(dto.getIrpp4());
        financeConfiguration.setIrpp5(dto.getIrpp5());

        // Fetch and set Annee based on anneeId
        if (dto.getAnneeId() != null) {
            financeConfiguration.setAnnee(anneeRepository.findById(dto.getAnneeId())
                    .orElseThrow(() -> new RuntimeException("Year not found with ID: " + dto.getAnneeId())));
        }

        return financeConfiguration;
    }

    private void mapToEntity(FinanceConfigurationDTO dto, FinanceConfiguration entity) {

        if (dto.getCnss() != 0) {
            entity.setCnss(dto.getCnss());
        }
        if (dto.getCss1() != 0) {
            entity.setCss1(dto.getCss1());
        }
        if (dto.getCss2() != 0) {
            entity.setCss2(dto.getCss2());
        }
        if (dto.getCss3() != 0) {
            entity.setCss3(dto.getCss3());
        }
        if (dto.getCss4() != 0) {
            entity.setCss4(dto.getCss4());
        }
        if (dto.getCss5() != 0) {
            entity.setCss5(dto.getCss5());
        }
        if (dto.getTva() != 0) {
            entity.setTva(dto.getTva());
        }
        if (dto.getDeduction() != 0) {
            entity.setDeduction(dto.getDeduction());
        }
        if (dto.getIrpp1() != 0) {
            entity.setIrpp1(dto.getIrpp1());
        }
        if (dto.getIrpp2() != 0) {
            entity.setIrpp2(dto.getIrpp2());
        }
        if (dto.getIrpp3() != 0) {
            entity.setIrpp3(dto.getIrpp3());
        }
        if (dto.getIrpp4() != 0) {
            entity.setIrpp4(dto.getIrpp4());
        }
        if (dto.getIrpp5() != 0) {
            entity.setIrpp5(dto.getIrpp5());
        }

        // Fetch and set Annee based on anneeId
        if (dto.getAnneeId() != null) {
            entity.setAnnee(anneeRepository.findById(dto.getAnneeId())
                    .orElseThrow(() -> new RuntimeException("Year not found with ID: " + dto.getAnneeId())));
        }
    }
}
