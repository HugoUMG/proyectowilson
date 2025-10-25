package com.mipymes.sistema_contable.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "catalogo_cuentas")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private AccountType tipo;
    @Enumerated(EnumType.STRING)
    private Naturaleza naturaleza;
}
