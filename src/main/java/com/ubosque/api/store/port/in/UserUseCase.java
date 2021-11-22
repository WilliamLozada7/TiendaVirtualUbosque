package com.ubosque.api.store.port.in;

import com.ubosque.api.store.domain.dto.GenericResponse;
import com.ubosque.api.store.domain.dto.UserLoginRequest;
import com.ubosque.api.store.domain.dto.UserLoginResponse;
import com.ubosque.api.store.domain.dto.UserRegisterRequest;
import com.ubosque.api.store.domain.dto.UserUpdatePasswordRequest;
import com.ubosque.api.store.domain.entity.User;

public interface UserUseCase {
	
	public GenericResponse<User> registerUser(UserRegisterRequest userRegisterRequest);
	
	public GenericResponse<UserLoginResponse> login(UserLoginRequest userLoginRequest);
	
	public GenericResponse<String> updatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest); 
}

