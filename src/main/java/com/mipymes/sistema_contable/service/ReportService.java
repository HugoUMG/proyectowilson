package com.mipymes.sistema_contable.service;

import com.mipymes.sistema_contable.model.Account;
import com.mipymes.sistema_contable.repository.AccountRepository;
import com.mipymes.sistema_contable.repository.JournalRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ReportService {
    private final JournalRepository journalRepository;
    private final AccountRepository accountRepository;

    public ReportService(JournalRepository journalRepository, AccountRepository accountRepository) {
        this.journalRepository = journalRepository;
        this.accountRepository = accountRepository;
    }

    public List<Map<String, Object>> generarBalanceComprobacion() {
        List<Account> cuentas = accountRepository.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Account c : cuentas) {
            BigDecimal debe = journalRepository.totalDebePorCuenta(c.getId());
            BigDecimal haber = journalRepository.totalHaberPorCuenta(c.getId());
            BigDecimal saldo = debe.subtract(haber);
            Map<String, Object> r = new HashMap<>();
            r.put("codigo", c.getCodigo());
            r.put("cuenta", c.getNombre());
            r.put("debe", debe);
            r.put("haber", haber);
            r.put("saldo", saldo);
            resultado.add(r);
        }
        return resultado;
    }
}
