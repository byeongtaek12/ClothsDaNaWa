package com.example.clothsdanawa.common.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.clothsdanawa.common.exception.BaseException;
import com.example.clothsdanawa.common.exception.ErrorCode;
import com.example.clothsdanawa.user.entity.User;
import com.example.clothsdanawa.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		// 인증코드와 클라이언트 정보에서 제공자(google) 추출
		String provider = userRequest.getClientRegistration().getClientId();

		// 사용자 정보 추출
		OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

		String providerId = oAuth2User.getAttribute("sub");

		if (oAuth2User.getAttribute("email") == null) {
			throw new OAuth2AuthenticationException(new BaseException(ErrorCode.BAD_REQUEST_EMAIL_ESSENTIAL)
				.getMessage());
		}
		String email = oAuth2User.getAttribute("email");

		if (userRepository.existsByEmail(email)) {
			throw new OAuth2AuthenticationException(new BaseException(ErrorCode.CONFLICT_EMAIL).getMessage());
		}

		User findedOrNewUser = userRepository.findByProviderAndProviderId(provider, providerId).orElseGet(() ->
			userRepository.save(User.createOauthUser(oAuth2User, email, provider, providerId, "USER"))
		);

		return new CustomOAuthUser(findedOrNewUser, oAuth2User.getAttributes());
	}
}
