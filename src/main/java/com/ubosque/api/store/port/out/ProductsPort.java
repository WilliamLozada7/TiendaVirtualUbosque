package com.ubosque.api.store.port.out;

import com.ubosque.api.store.domain.entity.Products;

public interface ProductsPort {
	
	public Products registerProduct(Products product);

	public String deleteProdcuts();
}
