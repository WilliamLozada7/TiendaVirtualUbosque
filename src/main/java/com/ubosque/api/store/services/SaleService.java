package com.ubosque.api.store.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ubosque.api.store.domain.dto.DetailSale;
import com.ubosque.api.store.domain.dto.request.SaleRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.entity.Sale;
import com.ubosque.api.store.port.in.SaleUseCase;
import com.ubosque.api.store.port.in.UserUseCase;
import com.ubosque.api.store.port.out.SalePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SaleService implements SaleUseCase {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsService.class);
	private final UserUseCase userUseCase;
	private final SalePort salePort;

	@Override
	public GenericResponse<String> registerSale(SaleRequest saleRequest, String authorization){
		
		LOGGER.info("** SaleService-RegisterSale-Init **");
		
		GenericResponse<String> genericResponse = new GenericResponse<>();
		
		try {
			userUseCase.validateSession(authorization);
			
			String count =  String.valueOf(salePort.countSale());
			
			if(count == null) {
				count = "0";
			}
			
			List<DetailSale> detailSales = new ArrayList<>();
			
			for(DetailSale detailSale : saleRequest.getSaleDatailSale()) {
				
				DetailSale detail = DetailSale.builder()
						.detailProductQuantity(detailSale.getDetailProductQuantity())
						.detailProductCode(detailSale.getDetailProductCode())
						.detailTotalValue(detailSale.getDetailTotalValue())
						.detailSalesValue(detailSale.getDetailSalesValue())
						.detailIvaValue(detailSale.getDetailIvaValue())
						.build();
				
				detailSales.add(detail);
			}
			
			Sale sale = Sale.builder()
					.saleUserId(saleRequest.getSaleUserId())
					.saleBranchOffice(saleRequest.getSaleBranchOffice())
					.saleCodeSale(count)
					.saleDetailSale(detailSales)
					.saleIvaSales(saleRequest.getSaleIvaSales())
					.saleTotalSale(saleRequest.getSaleTotalSale())
					.saleSalesValue(saleRequest.getSaleSalesValue())
					.build();
			
			salePort.registerSale(sale);
			
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Venta registrada exitosamente.");
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("No se pudo registrar la venta en la base de datos.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** ProviderService-RegisterSale-Finish **");
		return genericResponse;
	}
	
}
