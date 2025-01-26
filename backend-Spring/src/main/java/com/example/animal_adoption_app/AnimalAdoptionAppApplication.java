package com.example.animal_adoption_app;

import com.example.animal_adoption_app.model.User;
import com.example.animal_adoption_app.model.enums.UserRole;
import com.example.animal_adoption_app.repository.UserRepository;
import com.example.animal_adoption_app.security.SecurityConfiguration;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EnableScheduling
public class AnimalAdoptionAppApplication {
//    @Autowired
//    private UserRepository userRepository;


    public static void main(String[] args) {
        SpringApplication.run(AnimalAdoptionAppApplication.class, args);
    }
}
//        @PostConstruct
//        public void initAdminUser() {
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//            User adminUser = new User();
//            adminUser.setUsername("admin_efi");
//            adminUser.setPassword(passwordEncoder.encode("12345"));
//            adminUser.setRole(UserRole.ADMIN);
//            adminUser.setEmail("admin@example.com");
//            adminUser.setFirstName("Admin");
//            adminUser.setLastName("User");
//
//            userRepository.save(adminUser);
//            System.out.println("Admin user created successfully!");
//        }

