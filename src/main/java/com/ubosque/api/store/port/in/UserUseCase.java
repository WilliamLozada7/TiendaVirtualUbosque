package com.ubosque.api.store.port.in;

import com.ubosque.api.store.domain.dto.request.UserLoginRequest;
import com.ubosque.api.store.domain.dto.request.UserRegisterRequest;
import com.ubosque.api.store.domain.dto.request.UserUpdatePasswordRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.dto.response.UserLoginResponse;
import com.ubosque.api.store.domain.dto.response.ValidateSessionResponse;
import com.ubosque.api.store.domain.entity.User;

public interface UserUseCase {
	
	public GenericResponse<User> registerUser(UserRegisterRequest userRegisterRequest);
	
	public GenericResponse<UserLoginResponse> login(UserLoginRequest userLoginRequest);
	
	public GenericResponse<String> updatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest); 
	
	public ValidateSessionResponse validateSession(String token) throws Exception;
}

