package com.ubosque.api.store.domain.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdatePasswordRequest {

	@NotNull(message = "La contraseña antigua no puede ser null")
	private String oldPassword;
	
	@NotNull(message = "La contraseña nueva no puede ser null")
	private String newPassword;
	
	@NotNull(message = "El usuario no puede ser null")
	private String logon;
}
