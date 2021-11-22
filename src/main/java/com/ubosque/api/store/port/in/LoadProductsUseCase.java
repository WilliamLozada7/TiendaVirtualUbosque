package com.ubosque.api.store.port.in;

import com.ubosque.api.store.domain.dto.GenericResponse;
import com.ubosque.api.store.domain.dto.LoadProductsRequest;

public interface LoadProductsUseCase {
	
	GenericResponse<String> loadProducts(LoadProductsRequest loadProdutsRequest);

}
