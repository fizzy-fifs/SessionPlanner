package com.example.crowdfunding.user;

import com.example.crowdfunding.config.jwt.JwtUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner userRepoCommandLineRunner(UserRepository repository) {
        return args -> { repository.findAll(); };
    }

//    @Bean
//    public JwtUtil jwtUtilBean() {
//        return new JwtUtil();
//    }
}
