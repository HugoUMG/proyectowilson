package com.mipymes.sistema_contable.service;

import com.mipymes.sistema_contable.model.Mayor;
import com.mipymes.sistema_contable.model.JournalEntry;
import com.mipymes.sistema_contable.model.JournalLine;
import com.mipymes.sistema_contable.repository.MayorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Servicio que realiza traslado automático al mayor al guardar un asiento.
 * - Para cada línea del asiento crea un registro en la tabla mayor con saldo acumulado.
 */
@Service
public class PostingService {

    private final MayorRepository mayorRepository;

    public PostingService(MayorRepository mayorRepository) {
        this.mayorRepository = mayorRepository;
    }

    @Transactional
    public void postToMayor(JournalEntry entry) {
        for (JournalLine line : entry.getLineas()) {
            Long cuentaId = line.getCuentaId();
            BigDecimal debe = line.getDebe() == null ? BigDecimal.ZERO : line.getDebe();
            BigDecimal haber = line.getHaber() == null ? BigDecimal.ZERO : line.getHaber();

            // obtener último saldo de la cuenta si existe
            BigDecimal lastSaldo = mayorRepository.findTopByCuentaIdOrderByIdDesc(cuentaId)
                    .map(Mayor::getSaldo)
                    .orElse(BigDecimal.ZERO);

            BigDecimal newSaldo = lastSaldo.add(debe).subtract(haber);

            Mayor m = new Mayor();
            m.setCuentaId(cuentaId);
            m.setFecha(entry.getFecha() == null ? LocalDate.now() : entry.getFecha());
            m.setDescripcion(line.getDescripcion());
            m.setDebe(debe);
            m.setHaber(haber);
            m.setSaldo(newSaldo);

            mayorRepository.save(m);
        }
    }
}