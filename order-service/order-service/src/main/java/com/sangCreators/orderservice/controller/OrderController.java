package com.sangCreators.orderservice.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sangCreators.orderservice.dto.OrderRequest;
import com.sangCreators.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
	@TimeLimiter(name="inventory")
	// we need to change return type of our function due to TimeLimiter annotations
	@Retry(name="inventory")
	public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
		return CompletableFuture.supplyAsync(() ->	orderService.createOrder(orderRequest));
		
	}
	
	//fallback method for our circuitBreaker and its always have same signature as our functions
	public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,RuntimeException runtimeException) {
		return CompletableFuture.supplyAsync(() -> "OOPS!!! Something went wrong with your order. Please try after some time.");
	}
}
