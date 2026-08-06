package com.shubham.employeemanagementsystem.config;

import com.shubham.employeemanagementsystem.repository.AppUserRepository;
import com.shubham.employeemanagementsystem.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.shubham.employeemanagementsystem.entity.Role;
import com.shubham.employeemanagementsystem.entity.AppUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           AppUserRepository appUserRepository,
                           PasswordEncoder passwordEncoder) {

        this.roleRepository = roleRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByRoleName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRoleName("ROLE_ADMIN");

            roleRepository.save(adminRole);
        }
        if (roleRepository.findByRoleName("ROLE_USER").isEmpty()) {

            Role userRole = new Role();
            userRole.setRoleName("ROLE_USER");

            roleRepository.save(userRole);
        }
        if (appUserRepository.findByUsername("admin").isEmpty()) {
            String adminPassword = System.getenv("ADMIN_SEED_PASSWORD");
            if (adminPassword == null || adminPassword.isBlank()) {
                logger.warn("ADMIN_SEED_PASSWORD env var not set — falling back to a default password. " +
                        "Set ADMIN_SEED_PASSWORD before deploying anywhere public.");
                adminPassword = "changeMe123!";
            }

            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setEnabled(true);
            Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));
            admin.setRoles(Set.of(adminRole));
            appUserRepository.save(admin);
        }
        if (appUserRepository.findByUsername("user").isEmpty()) {
            String userPassword = System.getenv("USER_SEED_PASSWORD");
            if (userPassword == null || userPassword.isBlank()) {
                logger.warn("USER_SEED_PASSWORD env var not set — falling back to a default password. " +
                        "Set USER_SEED_PASSWORD before deploying anywhere public.");
                userPassword = "changeMe456!";
            }

            AppUser user = new AppUser();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode(userPassword));
            user.setEnabled(true);

            Role userRole = roleRepository.findByRoleName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

            user.setRoles(Set.of(userRole));

            appUserRepository.save(user);
        }
    }
}