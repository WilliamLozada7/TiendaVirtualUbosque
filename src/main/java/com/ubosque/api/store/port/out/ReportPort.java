package com.ubosque.api.store.port.out;

import java.util.List;

import com.ubosque.api.store.domain.entity.Sale;

public interface ReportPort {

	public List<Sale> reportSalesByUser();

}
