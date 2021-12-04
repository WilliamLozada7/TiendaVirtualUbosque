package com.ubosque.api.store.adapter.persistence;

import org.springframework.stereotype.Component;

import com.ubosque.api.store.adapter.repository.SaleRepository;
import com.ubosque.api.store.domain.entity.Sale;

import java.util.List;
import com.ubosque.api.store.port.out.ReportPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReportAdapter implements ReportPort{

	private final SaleRepository saleRepository;
	
	@Override
	public List<Sale> reportSalesByUser(){
		return saleRepository.findAll();
	}
}
