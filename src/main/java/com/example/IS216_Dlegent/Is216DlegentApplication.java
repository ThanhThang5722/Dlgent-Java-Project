package com.example.IS216_Dlegent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.IS216_Dlegent.config.CloudinaryConfig;

@SpringBootApplication
@EnableConfigurationProperties(CloudinaryConfig.class)
public class Is216DlegentApplication {
	public static void main(String[] args) {
		SpringApplication.run(Is216DlegentApplication.class, args);
		// secret 123
	}
}
