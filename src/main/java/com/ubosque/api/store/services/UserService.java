package com.ubosque.api.store.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ubosque.api.store.domain.dto.request.UserLoginRequest;
import com.ubosque.api.store.domain.dto.request.UserRegisterRequest;
import com.ubosque.api.store.domain.dto.request.UserUpdatePasswordRequest;
import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.dto.response.UserLoginResponse;
import com.ubosque.api.store.domain.dto.response.ValidateSessionResponse;
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
	
	private final String USER_ROL = "USER";
	
	@Override
	public GenericResponse<User> registerUser(UserRegisterRequest userRegisterRequest) {
		
		LOGGER.info("** UserService-RegisterUser-Init **");
		GenericResponse<User> userGenericResponse = new GenericResponse<>();
		
		try {	

			if(userPort.findByUserId(userRegisterRequest.getUserId()) != null ) {
				throw new Exception(String.format("El usuario con cédula %s ya se encuentra registrado.", userRegisterRequest.getUserId()));
			}
			
			if(userPort.findByUserLogon(userRegisterRequest.getUserLogon()) != null) {
				throw new Exception(String.format("El username: %s ya se encuentra registrado", userRegisterRequest.getUserLogon()));
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
					.userBranchOffice(userRegisterRequest.getUserBranchOffice())
					.userRole(USER_ROL)
					.build();
			
			User userRegister = userPort.registerUser(userNew);
			
			if(userRegister != null) {
				userGenericResponse.setState(GenericResponse.ESTADO_EXITOSO);
				userGenericResponse.setMessage("Usuario registrado con exito.");
			}
		}
		catch(Exception e) {
			userGenericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			userGenericResponse.setMessage("Usuario no registrado.");
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
			
			userGenericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			userGenericResponse.setMessage("Usuario loggeado con exito.");
			userGenericResponse.setResults(UserLoginResponse.builder()
					.state(userLogin.getUserState())
					.token(token)
					.name(userLogin.getUserName())
					.branchOffice(userLogin.getUserBranchOffice())
					.rol(userLogin.getUserRole())
					.build());
			
		}
		catch(Exception e) {
			userGenericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			userGenericResponse.setMessage("Hubo un problema al loggear el usuario, intente nuevamente.");
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
			
			userGenericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			userGenericResponse.setMessage("Usuario actualizado con exito.");
			userGenericResponse.setError("");
			userGenericResponse.setResults("Contraseña actualizada.");
			
		}
		catch(Exception e) {
			userGenericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			userGenericResponse.setMessage("Hubo un problema al actulizar la contraseña el usuario, intente nuevamente.");
			userGenericResponse.setError(e.getMessage());
		}
		LOGGER.info("** UserService-UpdatePassword-Finish **");
		return userGenericResponse;
	}
	
	@Override
	public ValidateSessionResponse validateSession(String token) throws Exception{
		
		LOGGER.info("** UserService-ValidateSession-Init **");
		ValidateSessionResponse validateSessionResponse = ValidateSessionResponse.builder().build();;
		
		try {
			User user = userPort.findByUserToken(token);
			if(user == null) {
				throw new Exception("Token no valido.");
			}
			Date currentDate = new Date();
			if(currentDate.after(user.getUserEffectiveDate())) {
				throw new Exception(String.format("La sesión del usuario %s expiro.",user.getUserLogon()));
			}
			validateSessionResponse.setUser(user);
			validateSessionResponse.setCurrentDate(currentDate);
		} catch (Exception e) {
			LOGGER.info(String.format("** Error validando el usuario [%s] **", e.getMessage()));
			throw new Exception(String.format("Error validando la sesión del usuario [%s]", e.getMessage()));
		}
		
		LOGGER.info("** UserService-ValidateSession-Finish **");
		return validateSessionResponse;
	}
	
	@Override
	public GenericResponse<List<User>> getUsers(String authorization){
		
		LOGGER.info("** UserService-GetUsers-Init **");
		GenericResponse<List<User>> genericResponse = new GenericResponse<>();
		
		try {
			validateSession(authorization);
			
			List<User> users = userPort.findUsers();
			
			if (users == null) {
				throw new Exception("No se encontraron clientes.");
			}
			
			List<User> userResponse = new ArrayList<User>();
			for(User userFind: users) {
				if(!userFind.getUserRole().equals("ADMIN")) {
					User user = User.builder()
							.userName(userFind.getUserName())
							.userId(userFind.getUserId())
							.userAddres(userFind.getUserAddres())
							.userEmail(userFind.getUserEmail())
							.userBranchOffice(userFind.getUserBranchOffice())
							.build();
					userResponse.add(user);
				}
			}
			
			genericResponse.setState(GenericResponse.ESTADO_EXITOSO);
			genericResponse.setMessage("Clientes encontrados.");
			genericResponse.setResults(userResponse	);
			
		} catch (Exception e) {
			genericResponse.setState(GenericResponse.ESTADO_NO_EXITOSO);
			genericResponse.setMessage("Hubo un problema al obtener los clientes, intente nuevamente.");
			genericResponse.setError(e.getMessage());
		}
		
		return genericResponse;
		
	}
}
