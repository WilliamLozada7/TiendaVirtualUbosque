package com.ubosque.api.store.port.in;

import com.ubosque.api.store.domain.dto.request.SaleRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;

public interface SaleUseCase {
	
	public GenericResponse<String> registerSale(SaleRequest saleRequest, String authorization);

}
