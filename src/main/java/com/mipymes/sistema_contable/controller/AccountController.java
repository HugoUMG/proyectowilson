package com.mipymes.sistema_contable.controller;

import com.mipymes.sistema_contable.model.Account;
import com.mipymes.sistema_contable.repository.AccountRepository;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/cuentas")
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public List<Account> listar() {
        return accountRepository.findAll();
    }

    @PostMapping
    public Account crear(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @PutMapping("/{id}")
    public Account actualizar(@PathVariable Long id, @RequestBody Account account) {
        Account existente = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
        existente.setCodigo(account.getCodigo());
        existente.setNombre(account.getNombre());
        existente.setTipo(account.getTipo());
        existente.setNaturaleza(account.getNaturaleza());
        return accountRepository.save(existente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!accountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        accountRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}