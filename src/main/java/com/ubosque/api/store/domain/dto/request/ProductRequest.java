package com.ubosque.api.store.domain.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
	
	@NotNull(message = "El código del producto no puede ser null")
	private Long productCode;
	
	@NotNull(message = "El nombre del producto no puede ser null")
	private String productName;
	
	@NotNull(message = "El nit del proveedor no puede ser null")
	private Long providerNIT;
	
	@NotNull(message = "El precio de compra del producto no puede ser null")
	private Double acquisitionPrice;
	
	@NotNull(message = "El iva de compra del producto no puede ser null")
	private Double acquisitionIVA;
	
	@NotNull(message = "El precio de venta del producto no puede ser null")
	private Double sellingPrice;

}
