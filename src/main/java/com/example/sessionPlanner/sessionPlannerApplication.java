package com.example.sessionPlanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication()
@EnableAutoConfiguration
@EnableWebMvc
public class sessionPlannerApplication extends WebMvcAutoConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(sessionPlannerApplication.class, args);
	}

}
