package com.ubosque.api.store.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class GenericResponse<T> {
	
	public static final String ESTADO_EXITOSO = "1";
	public static final String ESTADO_NO_EXITOSO = "0";
	
	private String estado;
	
	private String Mensaje;
	
	private String Error;
	
	private T data;
	
	public GenericResponse () {
		this.estado = ESTADO_NO_EXITOSO;
		this.Mensaje = "Validación no exitosa!";
	}
}
