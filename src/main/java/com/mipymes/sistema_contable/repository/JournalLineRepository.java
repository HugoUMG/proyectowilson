package com.mipymes.sistema_contable.repository;

import com.mipymes.sistema_contable.model.JournalLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface JournalLineRepository extends JpaRepository<JournalLine, Long> {

    @Query(value = "SELECT COALESCE(SUM(ld.debe),0) FROM linea_diario ld WHERE ld.cuenta_id = :cuentaId", nativeQuery = true)
    BigDecimal sumDebeByCuenta(@Param("cuentaId") Long cuentaId);

    @Query(value = "SELECT COALESCE(SUM(ld.haber),0) FROM linea_diario ld WHERE ld.cuenta_id = :cuentaId", nativeQuery = true)
    BigDecimal sumHaberByCuenta(@Param("cuentaId") Long cuentaId);

    @Query(value = "SELECT ld.* FROM linea_diario ld JOIN diario d ON ld.diario_id = d.id WHERE ld.cuenta_id = :cuentaId AND d.fecha BETWEEN :desde AND :hasta", nativeQuery = true)
    List<JournalLine> findByCuentaAndFechaBetween(@Param("cuentaId") Long cuentaId,
                                                  @Param("desde") LocalDate desde,
                                                  @Param("hasta") LocalDate hasta);
}
