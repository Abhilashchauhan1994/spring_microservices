package com.sangCreators.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.sangCreators.inventoryservice.model.Inventory;
import com.sangCreators.inventoryservice.respository.InventoryRepository;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepo) {
		return args -> {
			Inventory inventory =new Inventory();
			inventory.setSkuCode("Iphone_13");
			inventory.setQuantity(100);
			
			Inventory inventory1 =new Inventory();
			inventory1.setSkuCode("Iphone_13_gold");
			inventory1.setQuantity(0);
			
			inventoryRepo.save(inventory);
			inventoryRepo.save(inventory1);
		};
	}
}
