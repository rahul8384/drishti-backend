package com.drishti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DrishtiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrishtiBackendApplication.class, args);
	}

}
