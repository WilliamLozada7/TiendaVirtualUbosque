package com.ubosque.api.store.domain.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoadProductsRequest {

	@NotNull(message = "La URL del archivo CSV no puede ser null")
	private String urlProducts;
	
}
