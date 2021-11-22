package com.ubosque.api.store.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "T02_PRODUCTS")
public class Products {
	
	@Id
	private String _id;
	private Long productCode;
	private String productName;
	private Long providerNIT;
	private Double acquisitionPrice;
	private Double acquisitionIVA;
	private Double sellingPrice;

}
