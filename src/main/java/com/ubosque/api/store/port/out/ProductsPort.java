package com.ubosque.api.store.port.out;

import java.util.List;

import com.ubosque.api.store.domain.entity.Products;

public interface ProductsPort {
	
	public Products registerProduct(Products product);

	public String deleteProducts();
	
	public Products updateProduct(Products product);
	
	public Products findProductsById(String id);
	
	public List<Products> findProductsByAll();
	
	public Products findProductByCode(Long code);
	
	public String deleteProduct(String id);
}
