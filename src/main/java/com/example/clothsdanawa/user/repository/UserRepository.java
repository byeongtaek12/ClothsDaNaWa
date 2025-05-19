package com.example.clothsdanawa.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clothsdanawa.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	default User findByIdOrElseThrow(Long userId) {
		return findById(userId).orElseThrow(()-> new NullPointerException("사용자가 존재하지 않습니다"));
	}
}
