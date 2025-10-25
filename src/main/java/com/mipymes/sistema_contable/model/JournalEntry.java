package com.mipymes.sistema_contable.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "diario")
@Getter
@Setter
@NoArgsConstructor
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private String concepto;
    @Enumerated(EnumType.STRING)
    private TipoOperacion tipoOperacion;
    private String creadoPor;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "diario_id")
    private List<JournalLine> lineas;
}