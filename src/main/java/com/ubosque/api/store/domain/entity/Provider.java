package com.ubosque.api.store.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "T03_PROVIDER")
public class Provider {
	
	@Id
	private String _id;
	private String providerName;
	private String providerNIT;
	private String providerCity;
	private String providerEmail;
	private String providerPhone;

}
