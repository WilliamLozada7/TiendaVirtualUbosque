package com.ubosque.api.store.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {
	
	@NotNull(message = "El usuario no puede ser null")
	@NotBlank(message = "El usuario no puede ser vacio")
	private String logon;
	
	@NotNull(message = "La contraseña no puede ser null")
	@NotBlank(message = "El usuario no puede ser vacio")
	private String password;
}
