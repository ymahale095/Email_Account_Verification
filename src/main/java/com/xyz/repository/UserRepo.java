package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyz.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByEmail(String emaill);




	public User findByVerificationCode(String code);
	
}
