package com.example.crowdfunding.project;

import com.example.crowdfunding.geocoding.GeocodingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    CommandLineRunner projectRepoCommandLineRunner(ProjectRepository repository) {
        return args -> { repository.findAll(); };
    }

    @Bean
    public GeocodingService geocodingService() { return new GeocodingService(); }
}
