package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.KPIDTO;
import com.PFE.RH.Entities.KPI;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KPIMapper {

    KPI kpiDTOToKPI(KPIDTO kpiDTO);

    KPIDTO kpiToKPIDTO(KPI kpi);
}
