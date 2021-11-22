package com.ubosque.api.store.port.out;

import com.ubosque.api.store.domain.entity.User;

public interface UserPort {
	
	public User registerUser(User user);
	
	public User findByUserId(String userId);
	
	public User findByUserLogonAndUserPassword(String logon, String password);
	
	public User updateUser(User user);
}