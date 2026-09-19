package com.geniq.user.controller;

import com.geniq.user.model.Lead;
import com.geniq.user.repository.LeadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadRepository leadRepository;

    public LeadController(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @PostMapping
    public ResponseEntity<Lead> create(@RequestBody Lead lead) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leadRepository.save(lead));
    }

    @GetMapping
    public List<Lead> all() {
        return leadRepository.findAll();
    }
}
