package com.mipymes.sistema_contable.service;

import com.mipymes.sistema_contable.model.JournalEntry;
import com.mipymes.sistema_contable.repository.JournalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class JournalService {
    private final JournalRepository journalRepository;

    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    @Transactional
    public JournalEntry createEntry(JournalEntry entry) {
        BigDecimal totalDebe = entry.getLineas().stream()
                .map(l -> l.getDebe() == null ? BigDecimal.ZERO : l.getDebe())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalHaber = entry.getLineas().stream()
                .map(l -> l.getHaber() == null ? BigDecimal.ZERO : l.getHaber())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalDebe.compareTo(totalHaber) != 0) {
            throw new IllegalArgumentException("Asiento no cuadrado: total debe = " + totalDebe + ", total haber = " + totalHaber);
        }
        return journalRepository.save(entry);
    }
}
