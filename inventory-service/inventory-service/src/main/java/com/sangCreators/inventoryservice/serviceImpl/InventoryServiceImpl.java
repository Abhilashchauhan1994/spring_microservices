package com.sangCreators.inventoryservice.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sangCreators.inventoryservice.dto.InventoryResponse;
import com.sangCreators.inventoryservice.respository.InventoryRepository;
import com.sangCreators.inventoryservice.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	InventoryRepository inventoryRepo;
	
	@Override
	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skuCode) {		
		return inventoryRepo.findBySkuCodeIn(skuCode).stream()
				.map(inventory ->
					InventoryResponse.builder()
					.skuCode(inventory.getSkuCode())
					.isInStock(inventory.getQuantity()>0)
					.build()
				).toList();
	}

}
