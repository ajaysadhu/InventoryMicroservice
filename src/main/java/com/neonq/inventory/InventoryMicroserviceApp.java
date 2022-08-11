package com.neonq.inventory;


import com.neonq.inventory.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@EnableEurekaClient
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


