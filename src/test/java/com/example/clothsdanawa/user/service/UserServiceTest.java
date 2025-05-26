// package com.example.clothsdanawa.user.service;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.BDDMockito.*;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.security.crypto.password.PasswordEncoder;
//
// import com.example.clothsdanawa.common.exception.BaseException;
// import com.example.clothsdanawa.common.exception.ErrorCode;
// import com.example.clothsdanawa.common.security.CustomUserPrincipal;
// import com.example.clothsdanawa.user.dto.UserUpdateRequestDto;
// import com.example.clothsdanawa.user.dto.UserUpdateResponseDto;
// import com.example.clothsdanawa.user.entity.User;
// import com.example.clothsdanawa.user.repository.UserRepository;
//
// @ExtendWith(MockitoExtension.class)
// class UserServiceTest {
//
// 	@Mock
// 	private UserRepository userRepository;
//
// 	@Mock
// 	private PasswordEncoder passwordEncoder;
//
// 	@InjectMocks
// 	private UserService userService;
//
// 	private User testUser;
// 	private CustomUserPrincipal testPrincipal;
//
// 	@BeforeEach
// 	void setUp() {
// 		testUser = User.builder()
// 			.userId(1L)
// 			.name("name")
// 			.email("email")
// 			.password("password")
// 			.address("address")
// 			.build();
//
// 		testPrincipal = new CustomUserPrincipal(testUser);
// 	}
//
// 	@Test
// 	@DisplayName("사용자 정보 업데이트 - 성공")
// 	void updateUser_Success() {
// 		// given
// 		UserUpdateRequestDto requestDto = new UserUpdateRequestDto(
// 			"name", "email", "password", "address");
//
// 		given(userRepository.findByUserIdAndDeletedAtIsNullOrElseThrow(anyLong()))
// 			.willReturn(testUser);
// 		given(passwordEncoder.encode(anyString()))
// 			.willReturn("encodedNewPassword");
//
// 		// when
// 		UserUpdateResponseDto response = userService.updateUser(
// 			testPrincipal, 1L, requestDto);
//
// 		// then
// 		assertNotNull(response);
// 		assertEquals("newNickname", testUser.getName());
// 		assertEquals("encodedNewPassword", testUser.getPassword());
// 		verify(passwordEncoder).encode("newPassword");
// 	}
//
// 	@Test
// 	@DisplayName("사용자 정보 업데이트 - 본인 계정 아님")
// 	void updateUser_NotMine() {
// 		// given
// 		UserUpdateRequestDto requestDto = new UserUpdateRequestDto(
// 			"name1", "email1", "password1", "address1");
//
// 		given(userRepository.findByUserIdAndDeletedAtIsNullOrElseThrow(anyLong()))
// 			.willReturn(testUser);
//
// 		// when & then
// 		BaseException exception = assertThrows(BaseException.class,
// 			() -> userService.updateUser(testPrincipal, 2L, requestDto));
//
// 		assertEquals(ErrorCode.FORBIDDEN_NOT_MINE, exception.getErrorCode());
// 	}
//
// 	@Test
// 	@DisplayName("사용자 정보 업데이트 - 소셜 로그인 사용자 비밀번호 변경 불가")
// 	void updateUser_SocialUserChangePassword() {
// 		// given
// 		User socialUser = User.builder()
// 			.userId(1L)
// 			.email("social@example.com")
// 			.password(null) // 소셜 로그인 사용자
// 			.build();
//
// 		UserUpdateRequestDto requestDto = new UserUpdateRequestDto(
// 			"name", "email", "null", "address");
//
// 		given(userRepository.findByUserIdAndDeletedAtIsNullOrElseThrow(anyLong()))
// 			.willReturn(socialUser);
//
// 		// when & then
// 		BaseException exception = assertThrows(BaseException.class,
// 			() -> userService.updateUser(testPrincipal, 1L, requestDto));
//
// 		assertEquals(ErrorCode.FORBIDDEN_NOT_CHANGE, exception.getErrorCode());
// 	}
//
// 	@Test
// 	@DisplayName("사용자 삭제 - 성공")
// 	void deleteUser_Success() {
// 		// given
// 		given(userRepository.findByUserIdAndDeletedAtIsNullOrElseThrow(anyLong()))
// 			.willReturn(testUser);
//
// 		// when
// 		userService.deleteUser(testPrincipal, 1L);
//
// 		// then
// 		assertNotNull(testUser.getDeletedAt());
// 		verify(userRepository).findByUserIdAndDeletedAtIsNullOrElseThrow(1L);
// 	}
//
// 	@Test
// 	@DisplayName("사용자 삭제 - 본인 계정 아님")
// 	void deleteUser_NotMine() {
// 		// given
// 		given(userRepository.findByUserIdAndDeletedAtIsNullOrElseThrow(anyLong()))
// 			.willReturn(testUser);
//
// 		// when & then
// 		BaseException exception = assertThrows(BaseException.class,
// 			() -> userService.deleteUser(testPrincipal, 2L));
//
// 		assertEquals(ErrorCode.FORBIDDEN_NOT_MINE, exception.getErrorCode());
// 	}
// }