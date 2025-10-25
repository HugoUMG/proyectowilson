package com.mipymes.sistema_contable.repository;

import com.mipymes.sistema_contable.model.Mayor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MayorRepository extends JpaRepository<Mayor, Long> {
    Optional<Mayor> findTopByCuentaIdOrderByIdDesc(Long cuentaId);
}