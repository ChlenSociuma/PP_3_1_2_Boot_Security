package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public DatabaseInitializer(UserDetailsService userDetailsService,
                               UserService userService,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder
    ) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initializeRoles();
        initializeAdminUser();
    }

    private void initializeRoles() {
        if (roleRepository.findByRole("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        if (roleRepository.findByRole("ROLE_USER").isEmpty()) {
            roleRepository.save(new Role("ROLE_USER"));
        }
    }

    private void initializeAdminUser() {
        if (userDetailsService.loadUserByUsername("admin") == null) {
            Role adminRole = roleRepository.findByRole("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role 'ROLE_ADMIN' not found"));
            Role userRole = roleRepository.findByRole("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Role 'ROLE_USER' not found"));

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.getRoles().add(userRole);
            admin.getRoles().add(adminRole);
            userService.save(admin);
        }
    }
}
