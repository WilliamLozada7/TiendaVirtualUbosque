package com.ubosque.api.store.domain.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoadProductsRequest {

	@NotNull(message = "La URL del archivo CSV no puede ser null")
	private String urlProducts;
	
	@NotNull(message = "La fecha no puede ser null")
	private Date effectiveDate;
}
