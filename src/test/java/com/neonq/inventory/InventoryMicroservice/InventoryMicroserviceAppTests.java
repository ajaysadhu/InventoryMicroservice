package com.neonq.inventory.InventoryMicroservice;

import com.neonq.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class InventoryMicroserviceAppTests {
	@Autowired
	ProductService productService;
	@Test
	void contextLoads() {
	}
	@Test
	private  void  testPro() {
				ExecutorService executor = Executors.newFixedThreadPool(2);
				executor.execute(productService);
				//Thread.s
		// leep(2000); //-> adding a sleep here will break the transactions concurrency
				executor.execute(productService);

				executor.shutdown();
				try {
					executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
	}


}
