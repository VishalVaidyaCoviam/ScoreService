package com.example.scoreboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableCaching
@EnableEurekaClient
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ScoreboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreboardApplication.class, args);
	}

}
