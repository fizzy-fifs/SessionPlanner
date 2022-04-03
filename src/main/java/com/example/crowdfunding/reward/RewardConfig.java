package com.example.crowdfunding.reward;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class RewardConfig {
    @Bean
    CommandLineRunner rewardpoRepoommandLineRunner(RewardRepository repository) {
        return args -> { repository.findAll(); };
    }
}
