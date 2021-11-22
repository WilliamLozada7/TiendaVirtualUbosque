package com.ubosque.api.store.services;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ubosque.api.store.domain.dto.GenericResponse;
import com.ubosque.api.store.domain.dto.UserLoginRequest;
import com.ubosque.api.store.domain.dto.UserLoginResponse;
import com.ubosque.api.store.domain.dto.UserRegisterRequest;
import com.ubosque.api.store.domain.dto.UserUpdatePasswordRequest;
import com.ubosque.api.store.domain.entity.User;
import com.ubosque.api.store.port.in.UserUseCase;
import com.ubosque.api.store.port.out.UserPort;
import com.ubosque.api.store.util.Parameters;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserService implements UserUseCase{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	private final UserPort userPort;
	
	@Override
	public GenericResponse<User> registerUser(UserRegisterRequest userRegisterRequest) {
		
		LOGGER.info("** UserService-RegisterUser-Init **");
		GenericResponse<User> userGenericResponse = new GenericResponse<>();
		
		try {	

			if(userPort.findByUserId(userRegisterRequest.getUserId()) != null ) {
				throw new Exception(String.format("El usuario con cédula %s ya se encuentra registrado.", userRegisterRequest.getUserId()));
			}
			
	    	Date fechaActual = new Date();
	    	int vigencia = 30;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaActual);
			calendar.add(Calendar.MINUTE, vigencia);
			Date fechaSalida = calendar.getTime();
			
			String token = Parameters.getToken(userRegisterRequest.getUserId(), fechaActual, fechaSalida);
			
			User userNew = User.builder()
					.userId(userRegisterRequest.getUserId())
					.userAddres(userRegisterRequest.getUserAddres())
					.userEmail(userRegisterRequest.getUserEmail())
					.userName(userRegisterRequest.getUserName())
					.userLogon(userRegisterRequest.getUserLogon())
					.userPassword(Parameters.getHash(userRegisterRequest.getUserPassword()))
					.userToken(token)
					.userStartDate(fechaActual)
					.userEffectiveDate(fechaSalida)
					.userState(Parameters.ESTADO_ACTIVO)
					.build();
			
			User userRegister = userPort.registerUser(userNew);
			
			if(userRegister != null) {
				userGenericResponse.setEstado(GenericResponse.ESTADO_EXITOSO);
				userGenericResponse.setMensaje("Usuario registrado con exito.");
			}
		}
		catch(Exception e) {
			userGenericResponse.setEstado(GenericResponse.ESTADO_NO_EXITOSO);
			userGenericResponse.setMensaje("Usuario no registrado.");
			userGenericResponse.setError(e.getMessage());
		}
		
		LOGGER.info("** UserService-RegisterUser-Finish **");
		return userGenericResponse;
	}
	
	@Override
	public GenericResponse<UserLoginResponse> login(UserLoginRequest userLoginRequest) {
		
		LOGGER.info("** UserService-Login-Init **");
		GenericResponse<UserLoginResponse> userGenericResponse = new GenericResponse<>();
		
		try {
			
			User userLogin = userPort.findByUserLogonAndUserPassword(userLoginRequest.getLogon(), Parameters.getHash(userLoginRequest.getPassword()));
			
			if(userLogin == null) {
				throw new Exception ("Usuario o clave no valida.");
			}
			if(userLogin.getUserState().equals(Parameters.ESTADO_INACTIVO)) {
				throw new Exception ("Usuario inactivo.");
			}
			
			Date fechaActual = new Date();
	    	int vigencia = 30;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaActual);
			calendar.add(Calendar.MINUTE, vigencia);
			Date fechaSalida = calendar.getTime();
			
			String token = Parameters.getToken(userLogin.getUserId(), fechaActual, fechaSalida);
			
			userLogin.setUserToken(token);
			userLogin.setUserStartDate(fechaActual);
			userLogin.setUserEffectiveDate(fechaSalida);
			userPort.updateUser(userLogin);
			
			userGenericResponse.setEstado(GenericResponse.ESTADO_EXITOSO);
			userGenericResponse.setMensaje("Usuario loggeado con exito.");
			userGenericResponse.setError("");
			userGenericResponse.setData(UserLoginResponse.builder()
					.estado(userLogin.getUserState())
					.token(token)
					.build());
			
		}
		catch(Exception e) {
			userGenericResponse.setEstado(GenericResponse.ESTADO_NO_EXITOSO);
			userGenericResponse.setMensaje("Hubo un problema al loggear el usuario, intente nuevamente.");
			userGenericResponse.setError(e.getMessage());
		}
		LOGGER.info("** UserService-Login-Finish **");
		return userGenericResponse;
	}
	
	@Override
	public GenericResponse<String> updatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest){
		
		LOGGER.info("** UserService-UpdatePassword-Init **");
		GenericResponse<String> userGenericResponse = new GenericResponse<>();
		
		try {
			
			User userUpdate = userPort.findByUserLogonAndUserPassword(userUpdatePasswordRequest.getLogon(), Parameters.getHash(userUpdatePasswordRequest.getOldPassword()));
			
			if(userUpdate == null) {
				throw new Exception ("Usuario o clave no valida.");
			}
			if(userUpdate.getUserState().equals(Parameters.ESTADO_INACTIVO)) {
				throw new Exception ("Usuario inactivo.");
			}
			
			userUpdate.setUserPassword(Parameters.getHash(userUpdatePasswordRequest.getNewPassword()));
			userPort.updateUser(userUpdate);
			
			userGenericResponse.setEstado(GenericResponse.ESTADO_EXITOSO);
			userGenericResponse.setMensaje("Usuario actualizado con exito.");
			userGenericResponse.setError("");
			userGenericResponse.setData("Contraseña actualizada.");
			
		}
		catch(Exception e) {
			userGenericResponse.setEstado(GenericResponse.ESTADO_NO_EXITOSO);
			userGenericResponse.setMensaje("Hubo un problema al actulizar la contraseña el usuario, intente nuevamente.");
			userGenericResponse.setError(e.getMessage());
		}
		LOGGER.info("** UserService-UpdatePassword-Finish **");
		return userGenericResponse;
	}
}
