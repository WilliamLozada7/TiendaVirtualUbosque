package com.ubosque.api.store.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ubosque.api.store.domain.dto.request.ProviderRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.entity.Provider;
import com.ubosque.api.store.port.in.ProviderUseCase;
import com.ubosque.api.store.port.in.UserUseCase;
import com.ubosque.api.store.port.out.ProviderPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProviderService implements ProviderUseCase {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsService.class);
	private final UserUseCase userUseCase;
	private final ProviderPort providerPort;

	@Override
	public GenericResponse<String> registerProvider(ProviderRequest providerRequest, String authorization){
		
		LOGGER.info("** ProviderService-RegisterProvider-Init **");
		
		GenericResponse<String> genericResponse = new GenericResponse<>();
		
		try {
			userUseCase.validateSession(authorization);
			
			if(providerPort.findProviderByProviderNit(providerRequest.getProviderNIT()) != null) {
				throw new Exception(String.format("El proveedor con NIT: %s, ya se encuentra registrado.", providerRequest.getProviderNIT()));
			}
			
			Provider provider = Provider.builder()
					.providerName(providerRequest.getProviderName())
					.providerNIT(providerRequest.getProviderNIT())
					.providerEmail(providerRequest.getProviderEmail())
					.providerCity(providerRequest.getProviderCity())
					.build();
			
			providerPort.registerProvider(provider);
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Proveedor registrado de forma exitosa.");
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("No se pudo registrar el proveedor en la base de datos.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** ProviderService-RegisterProvider-Finish **");
		return genericResponse;
	}
	
	@Override
	public GenericResponse<String> updateProvider(String id, ProviderRequest providerRequest, String authorization){
		
		LOGGER.info("** ProviderService-UpdateProvider-Init **");
		
		GenericResponse<String> genericResponse = new GenericResponse<>();
		
		try {
			userUseCase.validateSession(authorization);
			
			Provider provider = providerPort.findProviderById(id);
			
			if(providerRequest.getProviderName() != null) {
				provider.setProviderName(providerRequest.getProviderName());
			}
			if(providerRequest.getProviderNIT() != null) {
				provider.setProviderNIT(providerRequest.getProviderNIT());
			}
			if(providerRequest.getProviderEmail() != null) {
				provider.setProviderEmail(providerRequest.getProviderEmail());
			}
			if(providerRequest.getProviderCity() != null) {
				provider.setProviderCity(providerRequest.getProviderCity());
			}
			
			providerPort.updateProvider(provider);
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Proveedor actualizado de forma exitosa.");
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("No se pudo actualizar el proveedor en la base de datos.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** ProviderService-UpdateProvider-Finish **");
		return genericResponse;
	}
	
	@Override
	public GenericResponse<Provider> getProvider(String id, String authorization){
		
		LOGGER.info("** ProviderService-GetProvider-Init **");
		
		GenericResponse<Provider> genericResponse = new GenericResponse<>();
		
		try {
			userUseCase.validateSession(authorization);
			
			Provider provider = providerPort.findProviderById(id);
			
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Proveedor encontrado.");
			genericResponse.setResults(provider);
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("No se pudo actualizar el proveedor en la base de datos.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** ProviderService-GetProvider-Finish **");
		return genericResponse;
	}
	
	@Override
	public GenericResponse<Provider> deleteProvider(String id, String authorization){
		
		LOGGER.info("** ProviderService-DeleteProvider-Init **");
		
		GenericResponse<Provider> genericResponse = new GenericResponse<>();
		
		try {
			userUseCase.validateSession(authorization);
			
			String state = providerPort.deleteProviderById(id);
			
			if(state != "OK") {
				throw new Exception("No se pudo eliminar el producto de la base de datos.");
			}
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Proveedor eliminado de forma exitosa.");
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("No se pudo eliminar el proveedor en la base de datos.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** ProviderService-DeleteProvider-Finish **");
		return genericResponse;
	}
}
