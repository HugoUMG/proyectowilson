package com.mipymes.sistema_contable.repository;

import com.mipymes.sistema_contable.model.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByCodigo(String codigo);
}