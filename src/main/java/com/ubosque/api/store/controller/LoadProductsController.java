package com.ubosque.api.store.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ubosque.api.store.domain.dto.GenericResponse;
import com.ubosque.api.store.domain.dto.LoadProductsRequest;
import com.ubosque.api.store.port.in.LoadProductsUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/store/loadProducts")
@RequiredArgsConstructor
public class LoadProductsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadProductsController.class);
	
	private final LoadProductsUseCase loadProductsUseCase;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/registerProducts")
	public GenericResponse<String> loadProducts (
			@Valid @RequestHeader("authorization") String authorization, 
			@Valid @RequestBody LoadProductsRequest loadProductsRequest){
				
		LOGGER.info("** LoadProductsController-LoadProducts-Init **");
		return loadProductsUseCase.loadProducts(loadProductsRequest, authorization);
	}
	
}
