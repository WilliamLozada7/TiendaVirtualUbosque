package com.ubosque.api.store.services;

import java.io.FileReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.ubosque.api.store.domain.dto.request.ProductRequest;
import com.ubosque.api.store.domain.dto.request.LoadProductsRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.entity.Products;
import com.ubosque.api.store.port.in.ProductsUseCase;
import com.ubosque.api.store.port.in.UserUseCase;
import com.ubosque.api.store.port.out.ProductsPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductsService implements ProductsUseCase{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsService.class);
	private final ProductsPort productsPort;
	private final UserUseCase userUseCase;
	
	public static final char SEPARADOR = ',';
	public static final char COMILLAS = '"';
	
	@Override
	public GenericResponse<String> loadProducts(LoadProductsRequest loadProdutsRequest, String authorization) {
		
		LOGGER.info("** LoadProductsService-LoadProducts-Init **");
		
		GenericResponse<String> genericResponse = new GenericResponse<>();
		CSVReader reader = null;
		
		try {
			
			String extension = loadProdutsRequest.getUrlProducts().split("\\.")[1];
			if(!extension.equals("csv")) {
				throw new Exception("La extensión de archivo no es correcta");
			}
			
			userUseCase.validateSession(authorization);
			
			String estado = productsPort.deleteProducts();
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
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Productos registrados de forma exitosa.");
		}
		catch(Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("Archivo no cargado con exito.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** LoadProductsService-LoadProducts-Finish **");
		return genericResponse;
	}
	
	@Override
	public GenericResponse<String> loadProduct(ProductRequest productRequest, String authorization){
		
		LOGGER.info("** LoadProductsService-LoadProduct-Init **");
		
		GenericResponse<String> genericResponse = new GenericResponse<>();
		
		try {
			
			userUseCase.validateSession(authorization);
			
			Products product = Products.builder()
					.productCode(productRequest.getProductCode())
					.productName(productRequest.getProductName())
					.providerNIT(productRequest.getProviderNIT())
					.acquisitionPrice(productRequest.getAcquisitionPrice())
					.acquisitionIVA(productRequest.getAcquisitionIVA())
					.sellingPrice(productRequest.getSellingPrice())
					.build();
			
			productsPort.registerProduct(product);
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Producto registrado de forma exitosa.");
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("Producto no registrado en la base de datos.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** LoadProductsService-LoadProduct-Finish **");
		return genericResponse;
	}
	
	@Override
	public GenericResponse<String> updateProduct(String id, ProductRequest productRequest, String authorization){
		
		LOGGER.info("** LoadProductsService-LoadProduct-Init **");
		
		GenericResponse<String> genericResponse = new GenericResponse<>();
		
		try {
			
			userUseCase.validateSession(authorization);
			
			Products product = productsPort.findProductsById(id);
			
			if(productRequest.getProductCode() != null ) {
				product.setProductCode(productRequest.getProductCode());
			}
			if(productRequest.getProductName() != null ) {
				product.setProductName(productRequest.getProductName());
			}
			if(productRequest.getProviderNIT() != null ) {
				product.setProviderNIT(productRequest.getProviderNIT());
			}
			if(productRequest.getAcquisitionPrice() != null ) {
				product.setAcquisitionPrice(productRequest.getAcquisitionPrice());
			}
			if(productRequest.getAcquisitionIVA() != null ) {
				product.setAcquisitionIVA(productRequest.getAcquisitionIVA());
			}
			if(productRequest.getSellingPrice() != null ) {
				product.setSellingPrice(productRequest.getSellingPrice());
			}
			
			productsPort.updateProduct(product);
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Producto actualizado de forma exitosa.");
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("Producto no se pudo actualizar.");
			genericResponse.setError(e.getMessage());
		}
		LOGGER.info("** LoadProductsService-LoadProduct-Finish **");
		return genericResponse;
	}

	@Override
	public GenericResponse<Products> getProduct(String id, String authorization){
		
		LOGGER.info("** LoadProductsService-GetProduct-Init **");
		
		GenericResponse<Products> genericResponse = new GenericResponse<>();
		
		try {
			
			userUseCase.validateSession(authorization);
			
			Products product = productsPort.findProductsById(id);
			
			if(product == null) {
				throw new Exception(String.format("No se encontro producto con el código: %s", id));
			}
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Producto encontrado.");
			genericResponse.setResults(product);
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("Error buscando el producto.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** LoadProductsService-GetProduct-Finish **");
		return genericResponse;
		
	}
	
	@Override
	public GenericResponse<List<Products>> getProduct(String authorization){
		
		LOGGER.info("** LoadProductsService-GetProduct-Init **");
		
		GenericResponse<List<Products>> genericResponse = new GenericResponse<>();
		
		try {
			
			userUseCase.validateSession(authorization);
			
			List<Products> product = productsPort.findProductsByAll();
			
			if(product == null) {
				throw new Exception("No se puedieron cargar los productos.");
			}
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Productos encontrados.");
			genericResponse.setResults(product);
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("Error buscando los productos.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** LoadProductsService-GetProduct-Finish **");
		return genericResponse;
		
	}
	
	@Override
	public GenericResponse<Products> getProduct(Long code, String authorization){
		
		LOGGER.info("** LoadProductsService-GetProduct-Init **");
		
		GenericResponse<Products> genericResponse = new GenericResponse<>();
		System.out.println(code);
		try {
			
			userUseCase.validateSession(authorization);
			
			Products product = productsPort.findProductByCode(code);
			
			if(product == null) {
				throw new Exception(String.format("No se encontro producto con el código: %s", code));
			}
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Producto encontrado.");
			genericResponse.setResults(product);
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("Error buscando el producto.");
			genericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** LoadProductsService-GetProduct-Finish **");
		return genericResponse;
		
	}
	
	@Override
	public GenericResponse<String> deleteProduct(String id, String authorization){
		
		LOGGER.info("** UserService-DeleteProduct-Init **");
		GenericResponse<String> genericResponse = new GenericResponse<>();
		
		try {
			
			userUseCase.validateSession(authorization);
			
			String estado = productsPort.deleteProduct(id);
			if(!estado.equals("OK")) {
				throw new Exception("No se pudo eliminar el producto de la base de datos.");
			}
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Producto elminado de forma exitosa.");
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("Hubo un problema eliminando el producto, intente nuevamente.");
			genericResponse.setError(e.getMessage());
		}
		LOGGER.info("** UserService-DeleteProduct-Finish **");
		return genericResponse;
		
	}
}
