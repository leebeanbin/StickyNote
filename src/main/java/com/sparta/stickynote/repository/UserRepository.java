package com.sparta.stickynote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.stickynote.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
