package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.GradeDTO;
import com.PFE.RH.Services.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping
    public ResponseEntity<List<GradeDTO>> getAllGrades() {
        List<GradeDTO> grades = gradeService.getAllGrades();
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GradeDTO> createGrade(@RequestBody GradeDTO gradeDTO) {
        GradeDTO createdGrade = gradeService.saveGrade(gradeDTO);
        return new ResponseEntity<>(createdGrade, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeDTO> getGradeById(@PathVariable("id") Long id) {
        GradeDTO gradeDTO = gradeService.getGradeById(id);
        return new ResponseEntity<>(gradeDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable("id") Long id, @RequestBody GradeDTO gradeDTO) {
        GradeDTO updatedGrade = gradeService.updateGrade(id, gradeDTO);
        return new ResponseEntity<>(updatedGrade, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GradeDTO> updatePartialGrade(@PathVariable("id") Long id, @RequestBody GradeDTO gradeDTO) {
        GradeDTO updatedGrade = gradeService.updatePartialGrade(id, gradeDTO);
        return new ResponseEntity<>(updatedGrade, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable("id") Long id) {
        gradeService.deleteGrade(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
