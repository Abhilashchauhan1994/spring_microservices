package com.sangCreators.inventoryservice.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sangCreators.inventoryservice.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	//Optional<Inventory> findBySkuCode(String skuCode);

	List<Inventory> findBySkuCodeIn(List<String> skuCode);

}
