package com.sangCreators.inventoryservice.service;

import java.util.List;

import com.sangCreators.inventoryservice.dto.InventoryResponse;

public interface InventoryService {
	
	public List<InventoryResponse> isInStock(List<String> skuCode);

}
