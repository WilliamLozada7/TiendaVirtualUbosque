package com.ubosque.api.store.domain.dto.response;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponse {
	
	@NotNull(message = "El estado no puede ser null")
	private String state;
	
	@NotNull(message = "El token no puede ser null")
	private String token;
	
	@NotNull(message = "El nombre no puede ser null")
	private String name;
	
	@NotNull(message = "La sucursal no puede ser null")
	private String branchOffice;
	
	@NotNull(message = "El rol no puede ser null")
	private String rol;
	
	
}
