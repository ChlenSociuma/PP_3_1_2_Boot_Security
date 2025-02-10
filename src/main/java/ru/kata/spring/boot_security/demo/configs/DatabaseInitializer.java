package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public DatabaseInitializer(UserDetailsService userDetailsService,
                               UserService userService,
                               RoleRepository roleRepository
    ) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
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
            roleRepository.save(new Role("ROLE_ADMIN", "ADMIN"));

        }
        if (roleRepository.findByRole("ROLE_USER").isEmpty()) {
            roleRepository.save(new Role("ROLE_USER", "USER"));
        }
    }

    private void initializeAdminUser() {
        if (userDetailsService.loadUserByUsername("admin") == null) {
            Role adminRole = roleRepository.findByRole("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role 'ROLE_ADMIN' not found"));
            Role userRole = roleRepository.findByRole("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Role 'ROLE_USER' not found"));

            User admin = new User();
            admin.setUsername("admin@mail.ru");
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setPassword("admin");
            admin.setAge(35);
            admin.getRoles().add(userRole);
            admin.getRoles().add(adminRole);
            userService.save(admin);
        }
    }
}
