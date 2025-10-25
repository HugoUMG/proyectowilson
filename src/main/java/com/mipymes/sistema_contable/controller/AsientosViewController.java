package com.mipymes.sistema_contable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/asientos")
public class AsientosViewController {

    @GetMapping
    public String listaAsientos(Model model) {
        return "asientos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoAsiento(Model model) {
        return "asientos/form";
    }
}