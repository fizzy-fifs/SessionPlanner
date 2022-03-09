package com.example.crowdfunding.adminsitrator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdministratorConfig {

    @Bean
    CommandLineRunner adminRepoCommandLineRunner(AdministratorRepository repository) {
        return args -> { repository.findAll(); };
    }
}
