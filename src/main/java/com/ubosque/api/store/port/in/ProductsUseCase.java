package com.ubosque.api.store.port.in;

import com.ubosque.api.store.domain.dto.request.ProductRequest;
import com.ubosque.api.store.domain.dto.request.LoadProductsRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.entity.Products;

public interface ProductsUseCase {
	
	GenericResponse<String> loadProducts(LoadProductsRequest loadProdutsRequest, String authorization);

	GenericResponse<String> loadProduct(ProductRequest productRequest, String authorization);
	
	GenericResponse<String> updateProduct(Long code, ProductRequest productRequest, String authorization);
	
	GenericResponse<Products> getProduct(Long code, String authorization);
}
