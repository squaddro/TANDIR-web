package com.squadro.tandir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class WebServer {
	private static final String[] origins = {"https://tandir.herokuapp.com", "http://tandir.herokuapp.com", "http://localhost:8080"};
	
	public static void main(String[] args){
		SpringApplication.run(WebServer.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(origins).allowCredentials(true).maxAge(3600);
			}
		};
	}
}