package com.example.crowdfunding;

import com.cloudinary.Cloudinary;
import com.example.crowdfunding.cloudinary.CloudinaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication()
@EnableAutoConfiguration
public class crowdfundingApplication {

	public static void main(String[] args) {
		SpringApplication.run(crowdfundingApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinaryConfig() {
		Map config = new HashMap();
		config.put("cloud_name", System.getenv("cloud_name"));
		config.put("api_key", System.getenv("api_key"));
		config.put("api_secret", System.getenv("api_secret"));
		Cloudinary cloudinary = new Cloudinary(config);
		return cloudinary;
	}

	@Bean
	public CloudinaryService cloudinaryService() {
		return new CloudinaryService(){};
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
}
