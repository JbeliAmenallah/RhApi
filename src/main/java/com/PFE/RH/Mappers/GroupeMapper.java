package com.PFE.RH.Mappers;

import com.PFE.RH.DTO.GroupeDTO;
import com.PFE.RH.Entities.Groupe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupeMapper {
    GroupeMapper INSTANCE = Mappers.getMapper(GroupeMapper.class);

    GroupeDTO groupeToGroupeDTO(Groupe groupe);

    Groupe groupeDTOToGroupe(GroupeDTO groupeDTO);
}
