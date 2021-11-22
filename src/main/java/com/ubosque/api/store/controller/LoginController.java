package com.ubosque.api.store.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ubosque.api.store.domain.dto.GenericResponse;
import com.ubosque.api.store.domain.dto.UserLoginRequest;
import com.ubosque.api.store.domain.dto.UserLoginResponse;
import com.ubosque.api.store.domain.dto.UserRegisterRequest;
import com.ubosque.api.store.domain.dto.UserUpdatePasswordRequest;
import com.ubosque.api.store.domain.entity.User;
import com.ubosque.api.store.port.in.UserUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/store/users")
@RequiredArgsConstructor
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	private final UserUseCase userUseCase;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/register")
	public GenericResponse<User> registerUser (
			@Valid @RequestBody UserRegisterRequest userRegisterRequest){
		
		LOGGER.info("** LoginController-registerUser-Init **");
		return userUseCase.registerUser(userRegisterRequest);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/login")
	public ResponseEntity<GenericResponse<UserLoginResponse>> loginUser (
			@Valid @RequestBody UserLoginRequest userLoginRequest) {
		
		LOGGER.info("** LoginController-LoginUser-Init **");
		return ResponseEntity.ok(userUseCase.login(userLoginRequest));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/updatePassword")
	public GenericResponse<String> updatePassword ( 
			@Valid @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest) {
		
		LOGGER.info("** LoginController-UpdatePassword-Init **");
		return userUseCase.updatePassword(userUpdatePasswordRequest);
	}
	
}
