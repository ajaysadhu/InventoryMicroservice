package com.neonq.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InventoryMicroserviceApp {


	public static void main(String[] args) {
		SpringApplication.run(InventoryMicroserviceApp.class, args);
	}


}


