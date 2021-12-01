package com.ubosque.api.store.port.out;

import com.ubosque.api.store.domain.entity.Sale;

public interface SalePort {

	public Sale registerSale(Sale sale);
	
	public Long countSale();
}
