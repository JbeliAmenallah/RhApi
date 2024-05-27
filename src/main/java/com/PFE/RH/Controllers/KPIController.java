package com.PFE.RH.Controllers;

import com.PFE.RH.DTO.KPIDTO;
import com.PFE.RH.Services.KPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kpi")
public class KPIController {

    @Autowired
    private KPIService kpiService;

    @GetMapping("{contactId}")
    public ResponseEntity<List<KPIDTO>> calculateKPIsForContact(@PathVariable Long contactId) {
        List<KPIDTO> kpis = kpiService.calculateKPIsForContact(contactId);
        return new ResponseEntity<>(kpis, HttpStatus.OK);
    }
}
