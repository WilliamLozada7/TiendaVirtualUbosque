package com.ubosque.api.store.adapter.persistence;

import org.springframework.stereotype.Component;

import com.ubosque.api.store.adapter.repository.SaleRepository;
import com.ubosque.api.store.domain.entity.Sale;
import com.ubosque.api.store.port.out.SalePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SaleAdapter implements SalePort{

	private final SaleRepository saleRepository;
	
	@Override
	public Sale registerSale(Sale sale) {
		return saleRepository.insert(sale);
	}
	
	@Override
	public Long countSale() {
		return saleRepository.count();
	}
	
}
