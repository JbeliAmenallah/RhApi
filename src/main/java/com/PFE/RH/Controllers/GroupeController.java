package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.GroupeDTO;
import com.PFE.RH.Services.GroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
public class GroupeController {

    @Autowired
    private GroupeService groupeService;

    @GetMapping
    public ResponseEntity<List<GroupeDTO>> getAllGroupes() {
        List<GroupeDTO> groupes = groupeService.getAllGroupes();
        return new ResponseEntity<>(groupes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupeDTO> createGroupe(@RequestBody GroupeDTO groupeDTO) {
        GroupeDTO createdGroupe = groupeService.saveGroupe(groupeDTO);
        return new ResponseEntity<>(createdGroupe, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupeDTO> getGroupeById(@PathVariable("id") Long id) {
        GroupeDTO groupeDTO = groupeService.getGroupeById(id);
        return new ResponseEntity<>(groupeDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupeDTO> updateGroupe(@PathVariable("id") Long id, @RequestBody GroupeDTO groupeDTO) {
        GroupeDTO updatedGroupe = groupeService.updateGroupe(id, groupeDTO);
        return new ResponseEntity<>(updatedGroupe, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GroupeDTO> updatePartialGroupe(@PathVariable("id") Long id, @RequestBody GroupeDTO groupeDTO) {
        GroupeDTO updatedGroupe = groupeService.updatePartialGroupe(id, groupeDTO);
        return new ResponseEntity<>(updatedGroupe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupe(@PathVariable("id") Long id) {
        groupeService.deleteGroupe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
