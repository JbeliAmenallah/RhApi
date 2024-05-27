package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.FinanceConfigurationDTO;
import com.PFE.RH.Entities.FinanceConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FinanceConfigurationMapper {

    FinanceConfigurationMapper INSTANCE = Mappers.getMapper(FinanceConfigurationMapper.class);

    @Mapping(target = "id", ignore = true)
    FinanceConfiguration financeConfigurationFromDto(FinanceConfigurationDTO financeConfigurationDTO);

    FinanceConfigurationDTO financeConfigurationToDto(FinanceConfiguration financeConfiguration);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cnss", source = "financeConfigurationDTO.cnss")
    @Mapping(target = "css1", source = "financeConfigurationDTO.css1")
    @Mapping(target = "css2", source = "financeConfigurationDTO.css2")
    @Mapping(target = "css3", source = "financeConfigurationDTO.css3")
    @Mapping(target = "css4", source = "financeConfigurationDTO.css4")
    @Mapping(target = "css5", source = "financeConfigurationDTO.css5")
    @Mapping(target = "tva", source = "financeConfigurationDTO.tva")
    @Mapping(target = "deduction", source = "financeConfigurationDTO.deduction")
    @Mapping(target = "irpp1", source = "financeConfigurationDTO.irpp1")
    @Mapping(target = "irpp2", source = "financeConfigurationDTO.irpp2")
    @Mapping(target = "irpp3", source = "financeConfigurationDTO.irpp3")
    @Mapping(target = "irpp4", source = "financeConfigurationDTO.irpp4")
    @Mapping(target = "irpp5", source = "financeConfigurationDTO.irpp5")
    void updateFinanceConfigurationFromDto(FinanceConfigurationDTO financeConfigurationDTO, @MappingTarget FinanceConfiguration financeConfiguration);

    @Mapping(target = "id", ignore = true)
    FinanceConfiguration dtoToFinanceConfiguration(FinanceConfigurationDTO financeConfigurationDTO);
}
