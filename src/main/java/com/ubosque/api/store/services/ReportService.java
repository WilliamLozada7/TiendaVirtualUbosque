package com.ubosque.api.store.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.dto.response.ReportBranchOffice;
import com.ubosque.api.store.domain.dto.response.ReportUser;
import com.ubosque.api.store.domain.entity.Sale;
import com.ubosque.api.store.port.in.ReportUseCase;
import com.ubosque.api.store.port.in.UserUseCase;
import com.ubosque.api.store.port.out.ReportPort;
import com.ubosque.api.store.port.out.UserPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReportService implements ReportUseCase {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsService.class);
	private final UserUseCase userUseCase;
	private final UserPort userPort;
	private final ReportPort reportPort;

	@Override
	public GenericResponse<List<ReportUser>> reportSalesByUser(String authorization){
		
		LOGGER.info("** ReportService-ReportSalesByUser-Init **");
		
		GenericResponse<List<ReportUser>> genericResponse = new GenericResponse<>();
		
		try {
			userUseCase.validateSession(authorization);
			
			List<Sale> findSales = reportPort.reportSalesByUser();
			Map<String, List<Sale>> listSales =
					findSales.stream().collect(Collectors.groupingBy(w -> w.getSaleUserId()));
			
			Set<String> keys = listSales.keySet();
			
			List<ReportUser> reportUsers = new ArrayList<ReportUser>();
			
	        for ( String key : keys ) {
	        	String nameUser = userPort.findByUserId(key).getUserName();
	        	Double totalSale = 0.0;
	        	for(Sale sale: listSales.get(key)) {
	        		totalSale += Double.parseDouble(sale.getSaleTotalSale());
	        	}
	        	ReportUser report = ReportUser.builder()
	        			.userName(nameUser)
	        			.userId(key)
	        			.userAmount(totalSale.toString())
	        			.build();
	        	reportUsers.add(report);
	        }
	       
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Consulta exitosa.");
			genericResponse.setResults(reportUsers);
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("No se pudo realizar la consulta del reporte.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** ReportService-ReportSalesByUser-Finish **");
		return genericResponse;
	}
	
	@Override
	public GenericResponse<List<ReportBranchOffice>> reportSalesByBranchOffice(String authorization){
		
		LOGGER.info("** ReportService-ReportSalesByUser-Init **");
		
		GenericResponse<List<ReportBranchOffice>> genericResponse = new GenericResponse<>();
		
		try {
			userUseCase.validateSession(authorization);
			 
			List<Sale> findSales = reportPort.reportSalesByUser();
			Map<String, List<Sale>> listSales =
					findSales.stream().collect(Collectors.groupingBy(w -> w.getSaleBranchOffice()));
			
			Set<String> keys = listSales.keySet();
			
			List<ReportBranchOffice> reportBranchOfficce = new ArrayList<ReportBranchOffice>();
			
	        for ( String key : keys ) {
	        	Double totalSale = 0.0;
	        	for(Sale sale: listSales.get(key)) {
	        		totalSale += Double.parseDouble(sale.getSaleTotalSale());
	        	}
	        	ReportBranchOffice report = ReportBranchOffice.builder()
	        			.branchOfficeName(key)
	        			.branchOfficeAmount(totalSale.toString())
	        			.build();
	        	
	        	reportBranchOfficce.add(report);
	        }
	       
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Consulta exitosa.");
			genericResponse.setResults(reportBranchOfficce);
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("No se pudo realizar la consulta del reporte.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** ReportService-ReportSalesByUser-Finish **");
		return genericResponse;
	}
}
