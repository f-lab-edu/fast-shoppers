package com.fastshoppers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FastshoppersApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastshoppersApplication.class, args);
	}

}
