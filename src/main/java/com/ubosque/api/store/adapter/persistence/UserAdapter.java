package com.ubosque.api.store.adapter.persistence;

import org.springframework.stereotype.Component;

import com.ubosque.api.store.adapter.repository.UserRepository;
import com.ubosque.api.store.domain.entity.User;
import com.ubosque.api.store.port.out.UserPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserPort{
	
	private final UserRepository userRepository;

	@Override
	public User registerUser(User user) {
		return userRepository.insert(user);
	}
	
	@Override
	public User findByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}
	
	@Override
	public User findByUserLogonAndUserPassword(String logon, String password) {
		return userRepository.findByUserLogonAndUserPassword(logon, password);
	}
	
	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public User findByUserToken(String token) {
		return userRepository.findByUserToken(token);
	}
}
