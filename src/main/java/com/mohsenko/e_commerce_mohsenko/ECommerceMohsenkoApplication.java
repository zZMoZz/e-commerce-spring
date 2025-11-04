package com.mohsenko.e_commerce_mohsenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ECommerceMohsenkoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceMohsenkoApplication.class, args);
	}

}
