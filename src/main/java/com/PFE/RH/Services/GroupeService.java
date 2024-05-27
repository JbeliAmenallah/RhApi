package com.PFE.RH.Services;

import com.PFE.RH.DTO.GroupeDTO;
import com.PFE.RH.Entities.Groupe;
import com.PFE.RH.Mappers.GroupeMapper;
import com.PFE.RH.Repositories.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupeService {

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private GroupeMapper groupeMapper;

    public List<GroupeDTO> getAllGroupes() {
        List<Groupe> groupes = groupeRepository.findAll();
        return groupes.stream()
                .map(groupeMapper::groupeToGroupeDTO)
                .collect(Collectors.toList());
    }

    public GroupeDTO saveGroupe(GroupeDTO groupeDTO) {
        Groupe groupe = groupeMapper.groupeDTOToGroupe(groupeDTO);
        Groupe savedGroupe = groupeRepository.save(groupe);
        return groupeMapper.groupeToGroupeDTO(savedGroupe);
    }

    public GroupeDTO getGroupeById(Long groupeId) {
        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new RuntimeException("Groupe not found with id: " + groupeId));
        return groupeMapper.groupeToGroupeDTO(groupe);
    }

    public GroupeDTO updateGroupe(Long groupeId, GroupeDTO updatedGroupeDTO) {
        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new RuntimeException("Groupe not found with id: " + groupeId));

        groupe.setLibele(updatedGroupeDTO.getLibele());

        Groupe updatedGroupe = groupeRepository.save(groupe);
        return groupeMapper.groupeToGroupeDTO(updatedGroupe);
    }

    public GroupeDTO updatePartialGroupe(Long groupeId, GroupeDTO updatedGroupeDTO) {
        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new RuntimeException("Groupe not found with id: " + groupeId));

        if (updatedGroupeDTO.getLibele() != null) {
            groupe.setLibele(updatedGroupeDTO.getLibele());
        }

        Groupe updatedGroupe = groupeRepository.save(groupe);
        return groupeMapper.groupeToGroupeDTO(updatedGroupe);
    }

    public void deleteGroupe(Long groupeId) {
        groupeRepository.deleteById(groupeId);
    }

    public List<GroupeDTO> getByGroupeLibele(String libele) {
        List<Groupe> groupes = groupeRepository.findByLibele(libele);
        return groupes.stream()
                .map(groupeMapper::groupeToGroupeDTO)
                .collect(Collectors.toList());
    }
}
