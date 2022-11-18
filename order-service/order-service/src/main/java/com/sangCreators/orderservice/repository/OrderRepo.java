package com.sangCreators.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sangCreators.orderservice.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

}
