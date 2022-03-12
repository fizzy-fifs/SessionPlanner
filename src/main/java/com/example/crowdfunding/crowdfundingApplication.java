package com.example.crowdfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication()
public class crowdfundingApplication {

	public static void main(String[] args) {
		SpringApplication.run(crowdfundingApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("/**");
//			}
//
//			@Override
//			public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//				registry.addResourceHandler("swagger-ui.html")
////						.addResourceLocations("classpath:/META-INF/resources/")
//				;
//			}
//		};
//	}

}
