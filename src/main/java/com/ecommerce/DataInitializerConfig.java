package com.ecommerce;

import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializerConfig {

    @Bean
    CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if (userRepository.findByUserName("admin").isEmpty()){
                User admin= new User();
                admin.setUserName("admin");
                admin.setPassword(passwordEncoder.encode("adminPass"));
                admin.setEnabled(true);
                admin.setRoles(Set.of(Role.ROLE_ADMIN));
                admin.setEmail("admin@ecommerce.com");
                userRepository.save(admin);
            }
        };
    }
}
