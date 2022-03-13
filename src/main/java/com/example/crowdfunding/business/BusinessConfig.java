package com.example.crowdfunding.business;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessConfig {

    @Bean
    CommandLineRunner businessRepoCommandLineRunner(BusinessRepository repository) {
        return args -> { repository.findAll(); };
    }
}
