package com.neonq.inventory;


import com.neonq.inventory.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableSwagger2
@EnableRetry
public class InventoryMicroserviceApp {

	@Autowired
	ProductService productService;
	public static void main(String[] args) {
		SpringApplication.run(InventoryMicroserviceApp.class, args);
	}



	/*public ApplicationRunner init() {
		return args -> {

			ExecutorService executor = Executors.newFixedThreadPool(2);
			executor.execute(productService);
			//Thread.sleep(2000); //-> adding a sleep here will break the transactions concurrency
			executor.execute(productService);

			executor.shutdown();
			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		};
	}*/

}


