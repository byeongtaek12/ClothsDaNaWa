package com.example.clothsdanawa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClothsDaNaWaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothsDaNaWaApplication.class, args);
	}

}
