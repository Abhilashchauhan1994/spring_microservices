package com.sangCreators.orderservice.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.sangCreators.orderservice.dto.InventoryResponse;
import com.sangCreators.orderservice.dto.OrderLineItemsDto;
import com.sangCreators.orderservice.dto.OrderRequest;
import com.sangCreators.orderservice.event.OrderPlacedEvent;
import com.sangCreators.orderservice.model.Order;
import com.sangCreators.orderservice.model.OrderLineItems;
import com.sangCreators.orderservice.repository.OrderRepo;
import com.sangCreators.orderservice.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	WebClient.Builder webClientBuilder; // webclient is used to comunicate with other services
	
	@Autowired
	KafkaTemplate<String, OrderPlacedEvent>  kafkaTemplate; 
	
	@Override
	public String createOrder(OrderRequest orderRequest) {
		Order order =new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineitems =orderRequest.getOrderLineItemsdtoList().stream()
							.map(this::mapToOrderLineItems).toList();
		
		order.setOrderLineItems(orderLineitems);
		
		List<String> skuCode = order.getOrderLineItems().stream().map(orderLineItems -> orderLineItems.getSkuCode()).toList();
		
		//calling Inventory service to check if items are present in inventory or not
		//using web client 
		InventoryResponse[] inventoryResponseArray=webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
				uriBuilder -> uriBuilder.queryParam("skuCode", skuCode).build())
				.retrieve().bodyToMono(InventoryResponse[].class).block();
		
		boolean allskuCode = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
		if(allskuCode) {
			orderRepo.save(order);	
			/* sending our event using kafka after order placed */
			kafkaTemplate.send("NotificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
			return "Your Order placed Successfully!";
		}else {
			throw new IllegalArgumentException("Product is not available in stock. Please try later!");
		}
		
	}

	public OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto ) {
		OrderLineItems orderLineitems = new OrderLineItems();
		orderLineitems.setPrice(orderLineItemsDto.getPrice());
		orderLineitems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineitems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineitems;
	}
}
