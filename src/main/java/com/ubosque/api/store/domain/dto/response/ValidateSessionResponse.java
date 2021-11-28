package com.ubosque.api.store.domain.dto.response;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.ubosque.api.store.domain.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateSessionResponse {
	
	@NotNull(message = "El usuario no puede ser null")
	private User user;
	
	@NotNull(message = "La fecha no puede ser null")
	private Date currentDate;
}
