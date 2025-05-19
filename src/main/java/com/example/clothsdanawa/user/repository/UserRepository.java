package com.example.clothsdanawa.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clothsdanawa.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);

	default User findByIdOrElseThrow(Long userId) {
		return findById(userId).orElseThrow(()-> new NullPointerException("사용자가 존재하지 않습니다."));
	}

	default User findByEmailOrElseThrow(String email) {
		return findByEmail(email).orElseThrow(()-> new NullPointerException("사용자가 존재하지 않습니다."));
	}
}
