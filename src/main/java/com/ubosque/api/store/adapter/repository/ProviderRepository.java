package com.ubosque.api.store.adapter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ubosque.api.store.domain.entity.Provider;

public interface ProviderRepository extends MongoRepository<Provider, String>{
	
	public Provider findProviderBy_id(String id);
	
	public Provider findProviderByProviderNIT(String nit);

}
