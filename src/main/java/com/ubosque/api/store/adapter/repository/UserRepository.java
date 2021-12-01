package com.ubosque.api.store.adapter.repository;

import org.springframework.stereotype.Repository;

import com.ubosque.api.store.domain.entity.User;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	
	public User findByUserId(String userId);
	
	public User findByUserLogonAndUserPassword(String logon, String password);
	
	public User findByUserToken(String token);
	
	public User findByUserLogon(String logon);
	
}
