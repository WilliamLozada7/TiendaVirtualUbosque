package com.ubosque.api.store.domain.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProviderRequest {

	@NotNull(message = "")
	private String providerName;
	
	@NotNull(message = "")
	private String providerNIT;
	
	@NotNull(message = "")
	private String providerCity;
	
	@NotNull(message = "")
	private String providerEmail;
	
	@NotNull(message = "")
	private String providerPhone;
}
