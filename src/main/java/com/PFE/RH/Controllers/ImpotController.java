package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.ImpotDTO;
import com.PFE.RH.Services.ImpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/impots")
public class ImpotController {

    @Autowired
    private ImpotService impotService;
    @PatchMapping("/{id}")
    public ResponseEntity<ImpotDTO> partialUpdateImpot(@PathVariable Long id, @RequestBody ImpotDTO partialImpotDTO) {
        ImpotDTO updatedImpot = impotService.partialUpdateImpot(id, partialImpotDTO);
        return ResponseEntity.ok(updatedImpot);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImpot(@PathVariable Long id) {
        boolean deleted = impotService.deleteImpot(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<ImpotDTO> createImpot(@RequestBody ImpotDTO impotDTO) {
        ImpotDTO createdImpot = impotService.saveImpot(impotDTO);
        return new ResponseEntity<>(createdImpot, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ImpotDTO>> getAllImpots() {
        List<ImpotDTO> allImpots = impotService.getAllImpots();
        return ResponseEntity.ok(allImpots);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ImpotDTO> getImpotById(@PathVariable Long id) {
        ImpotDTO impotDTO = impotService.getImpotById(id);
        return ResponseEntity.ok(impotDTO);
    }
}
