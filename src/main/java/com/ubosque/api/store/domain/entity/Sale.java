package com.ubosque.api.store.domain.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ubosque.api.store.domain.dto.DetailSale;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "T04_SALE")
public class Sale {
	
	@Id
	private String _id;
	private String saleUserId;
	private String saleCodeSale;
	private String saleBranchOffice;
	private List<DetailSale> saleDetailSale;
	private String saleIvaSales;
	private String saleTotalSale;
	private String saleSalesValue;

}
