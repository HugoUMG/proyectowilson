package com.mipymes.sistema_contable.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "mayor")
@Getter
@Setter
@NoArgsConstructor
public class Mayor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cuentaId;
    private LocalDate fecha;
    private String descripcion;
    private BigDecimal debe;
    private BigDecimal haber;
    private BigDecimal saldo;
}
