package com.ubosque.api.store.domain.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.ubosque.api.store.domain.dto.DetailSale;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleRequest {
	
	@NotNull(message = "La cédula del usuario no puede ser null")
	private String saleUserId;
	
	@NotNull(message = "La sede del usuario no puede ser null")
	private String saleBranchOffice;
	
	@NotNull(message = "Los detalles de la venta no puede ser null")
	private List<DetailSale> saleDatailSale;
	
	@NotNull(message = "El IVA de la venta no puede ser null")
	private String saleIvaSales;
	
	@NotNull(message = "El total de la venta no puede ser null")
	private String saleTotalSale;
	
	@NotNull(message = "El valor de la venta no puede ser null")
	private String saleSalesValue;
}
