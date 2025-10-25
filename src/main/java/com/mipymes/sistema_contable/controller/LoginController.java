package com.mipymes.sistema_contable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/")
    public String root() {
        return "redirect:/asientos";
    }
}