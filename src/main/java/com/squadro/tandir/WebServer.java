package com.squadro.tandir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class WebServer {
	public static void main(String[] args){
		SpringApplication.run(WebServer.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/signin").allowedOrigins("*");
				registry.addMapping("/signup").allowedOrigins("*");
				registry.addMapping("/recipe").allowedOrigins("*");
				registry.addMapping("/recipe/{id}").allowedOrigins("*");
				registry.addMapping("/user").allowedOrigins("*");
				registry.addMapping("/user/{id}").allowedOrigins("*");
				registry.addMapping("/addrecipe").allowedOrigins("*");
				registry.addMapping("/deleterecipe").allowedOrigins("*");
				registry.addMapping("/updaterecipe").allowedOrigins("*");
			}
		};
	}
}