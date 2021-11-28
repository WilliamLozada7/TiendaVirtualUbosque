package com.ubosque.api.store.adapter.persistence;

import org.springframework.stereotype.Component;

import com.ubosque.api.store.adapter.repository.ProductsRepository;
import com.ubosque.api.store.domain.entity.Products;
import com.ubosque.api.store.port.out.ProductsPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductsAdapter implements ProductsPort{
	
	private final ProductsRepository productsRepository;
	
	@Override
	public Products registerProduct(Products product) {
		return productsRepository.insert(product);
	}

	@Override
	public String deleteProducts() {
		String estado = "";
		try {
			 productsRepository.deleteAll();
			 estado = "OK";
		}
		catch (Exception e) {
			estado = e.getMessage();
		}
		return estado;
	}
	
	@Override
	public Products updateProduct(Products product) {
		return productsRepository.save(product);
	}
	
	@Override
	public Products findProductsByProductCode(Long code) {
		return productsRepository.findProductsByProductCode(code);
	}
}
