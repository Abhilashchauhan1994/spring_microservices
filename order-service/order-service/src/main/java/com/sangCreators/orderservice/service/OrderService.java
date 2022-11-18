package com.sangCreators.orderservice.service;

import com.sangCreators.orderservice.dto.OrderRequest;

public interface OrderService {

	public String createOrder(OrderRequest orderRequest);
	
}
