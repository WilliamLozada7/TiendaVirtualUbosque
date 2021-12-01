package com.ubosque.api.store.port.in;

import com.ubosque.api.store.domain.dto.request.ProductRequest;

import java.util.List;

import com.ubosque.api.store.domain.dto.request.LoadProductsRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.entity.Products;

public interface ProductsUseCase {
	
	public GenericResponse<String> loadProducts(LoadProductsRequest loadProdutsRequest, String authorization);

	public GenericResponse<String> loadProduct(ProductRequest productRequest, String authorization);
	
	public GenericResponse<String> updateProduct(String id, ProductRequest productRequest, String authorization);
	
	public GenericResponse<Products> getProduct(String id, String authorization);
	
	public GenericResponse<List<Products>> getProduct(String authorization);
	
	public GenericResponse<Products> getProduct(Long code, String authorization);
	
	public GenericResponse<String> deleteProduct(String id);
}
