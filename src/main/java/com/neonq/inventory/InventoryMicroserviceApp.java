package com.neonq.inventory;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan({"com.neonq.controller", "com.neonq.service"})
@EnableSwagger2
public class InventoryMicroserviceApp {

	public static void main(String[] args) {
		SpringApplication.run(InventoryMicroserviceApp.class, args);
	}

}
