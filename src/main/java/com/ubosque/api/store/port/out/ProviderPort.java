package com.ubosque.api.store.port.out;

import java.util.List;

import com.ubosque.api.store.domain.entity.Provider;

public interface ProviderPort {

	public Provider registerProvider(Provider provider);
	
	public Provider updateProvider(Provider provider);
	
	public Provider findProviderById(String id);
	
	public String deleteProviderById(String id);
	
	public Provider findProviderByProviderNit(String nit);
	
	public List<Provider> findProviders();
}
