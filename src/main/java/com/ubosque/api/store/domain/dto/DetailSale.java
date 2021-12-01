package com.ubosque.api.store.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailSale {

	private String detailProductQuantity;
	
	private String detailProductCode;
	
	private String detailTotalValue;
	
	private String detailSalesValue;
	
	private String detailIvaValue;
}
