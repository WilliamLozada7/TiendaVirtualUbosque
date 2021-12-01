package com.ubosque.api.store.adapter.persistence;

import java.util.List;

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
	public Products findProductsById(String id) {
		return productsRepository.findProductsBy_id(id);
	}
	
	@Override
	public List<Products> findProductsByAll() {
		return productsRepository.findAll();
	}
	
	@Override
	public Products findProductByCode(Long code) {
		return productsRepository.findProductsByProductCode(code);
	}
	
	@Override
	public String deleteProduct(String id) {
		String estado = "";
		try {
			productsRepository.deleteById(id);
			 estado = "OK";
		}
		catch (Exception e) {
			estado = e.getMessage();
		}
		return estado;
	}
}
