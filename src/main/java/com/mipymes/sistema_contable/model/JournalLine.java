package com.mipymes.sistema_contable.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "linea_diario")
@Getter
@Setter
@NoArgsConstructor
public class JournalLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nota: relationship simplificado: guardamos cuentaId en vez de @ManyToOne para evitar carga compleja ahora.
    private Long cuentaId;
    private String descripcion;
    private BigDecimal debe = BigDecimal.ZERO;
    private BigDecimal haber = BigDecimal.ZERO;
}