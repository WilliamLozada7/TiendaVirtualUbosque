package com.ubosque.api.store.util;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

public class Parameters {
	
	public static final String ESTADO_ACTIVO = "A";
	public static final String ESTADO_INACTIVO = "I";

    public static String getHash(String password) {
       if(password == null) {
    	   return null;
       }
	return DigestUtils.sha256Hex(password);
    }
    
    public static String getToken(String userId, Date fechaActual, Date fechaSalida) {
    	
    	String token = null;
    	
		String hash = String.valueOf(userId) + 
				String.valueOf(fechaActual) + 
				String.valueOf(fechaSalida);
		
		token = getHash(hash);
    	
    	return token;
    }
}