package com.mipymes.sistema_contable.controller;

import com.mipymes.sistema_contable.model.User;
import com.mipymes.sistema_contable.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                         @RequestParam String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "redirect:/login?error=exists";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRol("ROLE_ADMIN");
        userRepository.save(user);
        return "redirect:/login?registered=true";
    }
}