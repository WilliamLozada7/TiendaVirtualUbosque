package com.ubosque.api.store.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ubosque.api.store.domain.dto.request.SaleRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.port.in.SaleUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/store/sale")
@RequiredArgsConstructor
public class SaleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaleController.class);
	private final SaleUseCase saleUseCase;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/registerSale")
	public GenericResponse<String> registerSale (
			@Valid @RequestHeader("authorization") String authorization, 
			@Valid @RequestBody SaleRequest saleRequest){
				
		LOGGER.info("** SaleController-RegisterSale-Init **");
		return saleUseCase.registerSale(saleRequest, authorization);
	}
}
