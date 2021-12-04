package com.ubosque.api.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.ubosque.api.store.domain.dto.request.ProviderRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.entity.Provider;
import com.ubosque.api.store.port.in.ProviderUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/store/provider")
@RequiredArgsConstructor
public class ProviderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProviderController.class);
	private final ProviderUseCase providerUseCase;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/registerProvider")
	public GenericResponse<String> registerProvider (
			@Valid @RequestHeader("authorization") String authorization, 
			@Valid @RequestBody ProviderRequest providerRequest){
				
		LOGGER.info("** ProviderController-RegisterProvider-Init **");
		return providerUseCase.registerProvider(providerRequest, authorization);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/updateProvider/{id}")
	public GenericResponse<String> updateProvider (
			@Valid @PathVariable String id,
			@Valid @RequestHeader("authorization") String authorization, 
			@Valid @RequestBody ProviderRequest providerRequest){
				
		LOGGER.info("** ProviderController-UpdateProvider-Init **");
		return providerUseCase.updateProvider(id, providerRequest, authorization);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/getProviders")
	public GenericResponse<List<Provider>> getProviders (
			@Valid @RequestHeader("authorization") String authorization){
				
		LOGGER.info("** ProviderController-GetProviders-Init **");
		return providerUseCase.getProviders(authorization);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/getProvider/{id}")
	public GenericResponse<Provider> getProvider (
			@Valid @PathVariable String id,
			@Valid @RequestHeader("authorization") String authorization){
				
		LOGGER.info("** ProviderController-GetProvider-Init **");
		return providerUseCase.getProvider(id, authorization);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/deleteProvider/{id}")
	public GenericResponse<Provider> deleteProvider (
			@Valid @PathVariable String id,
			@Valid @RequestHeader("authorization") String authorization){
				
		LOGGER.info("** ProviderController-DeleteProvider-Init **");
		return providerUseCase.deleteProvider(id, authorization);
	}
}
