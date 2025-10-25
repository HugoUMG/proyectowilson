package com.mipymes.sistema_contable.repository;

import com.mipymes.sistema_contable.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface JournalRepository extends JpaRepository<JournalEntry, Long> {

    // Consultas agregadas sobre linea_diario: como JournalEntry no mapea directamente a linea_diario para queries nativas,
    // usaremos consultas nativas para obtener totales por cuenta en la Entrega 2.
    @Query(value = "SELECT COALESCE(SUM(ld.debe),0) FROM linea_diario ld WHERE ld.cuenta_id = :cuentaId", nativeQuery = true)
    BigDecimal totalDebePorCuenta(@Param("cuentaId") Long cuentaId);

    @Query(value = "SELECT COALESCE(SUM(ld.haber),0) FROM linea_diario ld WHERE ld.cuenta_id = :cuentaId", nativeQuery = true)
    BigDecimal totalHaberPorCuenta(@Param("cuentaId") Long cuentaId);
}