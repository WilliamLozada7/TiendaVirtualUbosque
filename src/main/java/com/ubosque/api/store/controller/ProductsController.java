package com.ubosque.api.store.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ubosque.api.store.domain.dto.request.ProductRequest;
import com.ubosque.api.store.domain.dto.request.LoadProductsRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.entity.Products;
import com.ubosque.api.store.port.in.ProductsUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/store/loadProducts")
@RequiredArgsConstructor
public class ProductsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);
	
	private final ProductsUseCase productsUseCase;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/registerProducts")
	public GenericResponse<String> loadProducts (
			@Valid @RequestHeader("authorization") String authorization, 
			@Valid @RequestBody LoadProductsRequest loadProductsRequest){
				
		LOGGER.info("** LoadProductsController-LoadProducts-Init **");
		return productsUseCase.loadProducts(loadProductsRequest, authorization);
	}
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/registerProduct")
	public GenericResponse<String> loadProduct(
			@Valid @RequestHeader("authorization") String authorization,
			@Valid @RequestBody ProductRequest loadProductRequest){
		
		LOGGER.info("** LoadProductsController-LoadProduct-Init **");
		return productsUseCase.loadProduct(loadProductRequest, authorization);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/updateProduct/{code}")
	public GenericResponse<String> updateProduct(
			@Valid @PathVariable Long code,
			@Valid @RequestHeader ("authorization") String authorization, 
			@Valid @RequestBody ProductRequest productRequest){
		
		LOGGER.info("** LoadProductsController-LoadProduct-Init **");
		return productsUseCase.updateProduct(code, productRequest, authorization);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/getProduct/{code}")
	public GenericResponse<Products> getProduct(
			@Valid @PathVariable Long code,
			@Valid @RequestHeader ("authorization") String authorization){
		LOGGER.info("** LoadProductsController-LoadProduct-Init **");
		return productsUseCase.getProduct(code, authorization);
	}
	
}
