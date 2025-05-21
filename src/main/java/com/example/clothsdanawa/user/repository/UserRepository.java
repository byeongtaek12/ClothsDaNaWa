package com.example.clothsdanawa.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clothsdanawa.common.exception.BaseException;
import com.example.clothsdanawa.common.exception.ErrorCode;
import com.example.clothsdanawa.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailAndDeletedAtIsNull(String email);

	List<User> findAllByDeletedAtIsNull();

	Optional<User> findByUserIdAndDeletedAtIsNull(Long userId);

	boolean existsByEmail(String email);

	// default User findByIdOrElseThrow(Long userId) {
	// 	return findById(userId).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER_BY_ID));
	// }

	default User findByUserIdAndDeletedAtIsNullOrElseThrow(Long userId) {
		return findByUserIdAndDeletedAtIsNull(userId).orElseThrow(() -> new BaseException(
			ErrorCode.NOT_FOUND_USER_BY_ID));
	}

	default User findByEmailAndDeletedAtIsNullOrElseThrow(String email) {
		return findByEmailAndDeletedAtIsNull(email).orElseThrow(() -> new BaseException(
			ErrorCode.NOT_FOUND_USER_BY_EMAIL));
	}
}
