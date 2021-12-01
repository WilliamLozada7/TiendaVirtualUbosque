package com.ubosque.api.store.adapter.persistence;

import org.springframework.stereotype.Component;

import com.ubosque.api.store.adapter.repository.ProviderRepository;
import com.ubosque.api.store.domain.entity.Provider;
import com.ubosque.api.store.port.out.ProviderPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProviderAdapter implements ProviderPort{

	private final ProviderRepository providerRepository;
	
	@Override
	public Provider registerProvider(Provider provider) {
		return providerRepository.insert(provider);
	}
	
	@Override
	public Provider updateProvider(Provider provider) {
		return providerRepository.save(provider);
	}
	
	@Override
	public Provider findProviderById(String id) {
		return providerRepository.findProviderBy_id(id);
	}
	
	@Override
	public String deleteProviderById(String id) {
		String estado = "";
		try {
			 providerRepository.deleteById(id);
			 estado = "OK";
		}
		catch (Exception e) {
			estado = e.getMessage();
		}
		return estado;
	}
	
	@Override
	public Provider findProviderByProviderNit(String nit) {
		return providerRepository.findProviderByProviderNIT(nit);
	}
}
