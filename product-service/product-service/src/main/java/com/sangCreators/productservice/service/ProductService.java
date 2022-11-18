package com.sangCreators.productservice.service;

import java.util.List;

import com.sangCreators.productservice.dto.ProductRequest;
import com.sangCreators.productservice.dto.ProductResponse;

public interface ProductService {

	public void createProduct(ProductRequest productRequest);

	public List<ProductResponse> getAllProduct();
}
