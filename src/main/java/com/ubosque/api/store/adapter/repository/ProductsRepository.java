package com.ubosque.api.store.adapter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ubosque.api.store.domain.entity.Products;


public interface ProductsRepository extends MongoRepository<Products, String>{
	
	public Products findProductsBy_id(String id);
	
	public Products findProductsByProductCode(Long code); //4
}
