package com.mipymes.sistema_contable.controller;

import com.mipymes.sistema_contable.model.JournalEntry;
import com.mipymes.sistema_contable.repository.JournalRepository;
import com.mipymes.sistema_contable.service.JournalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diario")
public class JournalController {
    private final JournalService journalService;
    private final JournalRepository journalRepository;

    public JournalController(JournalService journalService, JournalRepository journalRepository) {
        this.journalService = journalService;
        this.journalRepository = journalRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearAsiento(@RequestBody JournalEntry entry) {
        try {
            JournalEntry saved = journalService.createEntry(entry);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    @GetMapping
    public List<JournalEntry> listarAsientos() {
        return journalRepository.findAll();
    }
}