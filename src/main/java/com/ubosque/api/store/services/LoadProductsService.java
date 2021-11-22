package com.ubosque.api.store.services;

import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.ubosque.api.store.domain.dto.GenericResponse;
import com.ubosque.api.store.domain.dto.LoadProductsRequest;
import com.ubosque.api.store.domain.entity.Products;
import com.ubosque.api.store.port.in.LoadProductsUseCase;
import com.ubosque.api.store.port.out.ProductsPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoadProductsService implements LoadProductsUseCase{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadProductsService.class);
	private final ProductsPort productsPort;
	
	public static final char SEPARADOR = ',';
	public static final char COMILLAS = '"';
	
	@Override
	public GenericResponse<String> loadProducts(LoadProductsRequest loadProdutsRequest) {
		
		LOGGER.info("** LoadProductsService-LoadProducts-Init **");
		
		GenericResponse<String> genericResponse = new GenericResponse<>();
		CSVReader reader = null;
		
		try {
			
			String extension = loadProdutsRequest.getUrlProducts().split("\\.")[1];
			if(!extension.equals("csv")) {
				throw new Exception("La extensión de archivo no es correcta");
			}
			
			String estado = productsPort.deleteProdcuts();
			if(!estado.equals("OK")) {
				throw new Exception("No se pudieron eliminar los productos de la base de datos.");
			}
			
			reader = new CSVReader(new FileReader(loadProdutsRequest.getUrlProducts()), SEPARADOR, COMILLAS);
			String[] line = null;
			while ((line = reader.readNext()) != null) {
				Products product = Products.builder()
						.productCode(Long.parseLong(line[0]))
						.productName(line[1])
						.providerNIT(Long.parseLong(line[2]))
						.acquisitionPrice(Double.parseDouble(line[3]))
						.acquisitionIVA(Double.parseDouble(line[4]))
						.sellingPrice(Double.parseDouble(line[5]))
						.build();
				productsPort.registerProduct(product);
			}
			reader.close();
			genericResponse.setEstado(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMensaje("Productos registrados de forma exitosa.");
		}
		catch(Exception e) {
			genericResponse.setEstado(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMensaje("Archivo no cargado con exito.");
			genericResponse.setError(e.getMessage());
		}
		
		return genericResponse;
	}

}
