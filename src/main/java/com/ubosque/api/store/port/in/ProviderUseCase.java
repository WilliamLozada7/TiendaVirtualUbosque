package com.ubosque.api.store.port.in;

import com.ubosque.api.store.domain.dto.request.ProviderRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.entity.Provider;

public interface ProviderUseCase {
	
	public GenericResponse<String> registerProvider(ProviderRequest providerRequest, String authorization);
	
	public GenericResponse<String> updateProvider(String id, ProviderRequest providerRequest, String authorization);
	
	public GenericResponse<Provider> getProvider(String id, String authorization);
	
	public GenericResponse<Provider> deleteProvider(String id, String authorization);
}
