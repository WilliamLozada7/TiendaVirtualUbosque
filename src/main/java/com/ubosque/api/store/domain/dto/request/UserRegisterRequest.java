package com.ubosque.api.store.domain.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {

	@NotNull(message = "La cedula no puede ser null")
	private String userId;
	
	@NotNull(message = "La direccion no puede ser null")
	private String userAddres;
	
	@NotNull(message = "El correo no puede ser null")
	private String userEmail;
	
	@NotNull(message = "El nombre no puede ser null")
	private String userName;
	
	@NotNull(message = "El usuario no puede ser null")
	private String userLogon;
	
	@NotNull(message = "La contraseña no puede ser null")
	private String userPassword;
	
	@NotNull(message = "La sede no puede ser null")
	private String userBranchOffice;
}
