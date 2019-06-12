package com.squadro.tandir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class WebServer {
	private static final String[] origins = {"http://tandir.herokuapp.com", "http://localhost:8080"};
	
	public static void main(String[] args){
		SpringApplication.run(WebServer.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/signin").allowedOrigins(origins);
				registry.addMapping("/signup").allowedOrigins(origins);
				registry.addMapping("/recipe").allowedOrigins(origins);
				registry.addMapping("/recipe/{id}").allowedOrigins(origins);
				registry.addMapping("/user").allowedOrigins(origins);
				registry.addMapping("/user/{id}").allowedOrigins(origins);
				registry.addMapping("/addrecipe").allowedOrigins(origins);
				registry.addMapping("/deleterecipe").allowedOrigins(origins);
				registry.addMapping("/updaterecipe").allowedOrigins(origins);
			}
		};
	}
}