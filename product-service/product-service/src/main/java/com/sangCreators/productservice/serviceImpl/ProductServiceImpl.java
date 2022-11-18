package com.sangCreators.productservice.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sangCreators.productservice.dto.ProductRequest;
import com.sangCreators.productservice.dto.ProductResponse;
import com.sangCreators.productservice.model.Product;
import com.sangCreators.productservice.respository.ProductReposiotry;
import com.sangCreators.productservice.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

	
	  @Autowired 
	  ProductReposiotry productRepo;
	 
	
	@Override
	public void createProduct(ProductRequest productRequest) {
		Product product =Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice()).build();

		productRepo.save(product);
		log.info("Product {} is saved",product.getId());
	}

	@Override
	public List<ProductResponse> getAllProduct() {
		List<Product> products = productRepo.findAll();
		
		return products.stream().map(this::mapToProductResponse).toList();
	}

	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice()).build();
	}

}
