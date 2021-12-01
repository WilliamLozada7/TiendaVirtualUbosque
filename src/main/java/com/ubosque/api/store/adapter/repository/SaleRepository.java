package com.ubosque.api.store.adapter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ubosque.api.store.domain.entity.Sale;

public interface SaleRepository extends MongoRepository<Sale, String>{

}
