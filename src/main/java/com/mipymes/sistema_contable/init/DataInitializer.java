package com.mipymes.sistema_contable.init;


import com.mipymes.sistema_contable.model.User;
import com.mipymes.sistema_contable.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Crea usuario admin al inicio si no existe: usuario=admin, password=admin123
 * Cambia la contraseña en producción.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User u = new User();
            u.setUsername("admin");
            u.setPassword(passwordEncoder.encode("admin123"));
            u.setRol("ROLE_ADMIN");
            userRepository.save(u);
            System.out.println("Usuario admin creado: usuario=admin password=admin123");
        } else {
            System.out.println("Usuario admin ya existe");
        }
    }
}