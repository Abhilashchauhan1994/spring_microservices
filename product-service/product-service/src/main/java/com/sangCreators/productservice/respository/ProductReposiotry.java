package com.sangCreators.productservice.respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sangCreators.productservice.model.Product;

public interface ProductReposiotry extends MongoRepository<Product, String> {

}
