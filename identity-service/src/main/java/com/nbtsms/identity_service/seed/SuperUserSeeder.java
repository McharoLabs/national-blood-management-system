package com.nbtsms.identity_service.seed;

import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.enums.Role;
import com.nbtsms.identity_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Configuration
public class SuperUserSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SuperUserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner seedAdminUser() {
        return args -> {
            Optional<User> adminExists = userRepository.findByEmail("mcharoprofg23@gmail.com");

            if (adminExists.isEmpty()) {
                User admin = new User();
                admin.setFirstName("Godfrey");
                admin.setMiddleName(null);
                admin.setLastName("Mcharo");
                admin.setEmail("mcharoprofg23@gmail.com");
                admin.setPhoneNumber("+255746561545");
                admin.setPassword(passwordEncoder.encode("Mcharo12!"));
                admin.setRoles(List.of(Role.SUPER_USER));

                userRepository.save(admin);
                System.out.println("✅ Admin user created.");
            } else {
                System.out.println("ℹ️ Admin user already exists.");
            }
        };
    }
}
