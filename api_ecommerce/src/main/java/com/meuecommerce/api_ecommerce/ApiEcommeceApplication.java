package com.meuecommerce.api_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.meuecommerce.api_ecommerce", "com.meuecommerce.api_ecommece"})
@EntityScan(basePackages = {"com.meuecommerce.api_ecommerce.model", "com.meuecommerce.api_ecommece.model"})

public class ApiEcommeceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiEcommeceApplication.class, args);
	}

}

