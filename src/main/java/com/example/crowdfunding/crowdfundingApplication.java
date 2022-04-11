package com.example.crowdfunding;

import com.cloudinary.Cloudinary;
import com.example.crowdfunding.cloudinary.CloudinaryService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.stripe.Stripe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication()
@EnableAutoConfiguration
@EnableWebMvc
public class crowdfundingApplication extends WebMvcAutoConfiguration {

//	@PostConstruct
//	public void setup() {
//		Stripe.apiKey = "sk_test_51KeDy4FkYRYTO3iFNb2qqLkowbG3kchP8NnHiJxsiJxlqXgnA2417cqAOgSgjygFjjvjyxqrlT336iH9WUI2tfaj00ALSQuIdp";
//	}

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
	@Primary
	public ObjectMapper objectMapper() {
		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
			@Override
			public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

			}
		});
		return new ObjectMapper()
				.setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.registerModule(module);
	}

//	@Bean
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}
}
