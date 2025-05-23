package com.example.clothsdanawa.common.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.clothsdanawa.common.exception.BaseException;
import com.example.clothsdanawa.common.security.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		// if (request.getRequestURI().startsWith("/auth")) {
		// 	filterChain.doFilter(request, response);
		// 	return;
		// }

		try {
			String bearerJwt = request.getHeader("Authorization");

			if (bearerJwt == null || !bearerJwt.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}

			String jwt = jwtUtil.substringToken(bearerJwt);
			Claims claims = jwtUtil.extractClaims(jwt);

			if (claims != null) {
				String email = claims.get("email", String.class);
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);

				UsernamePasswordAuthenticationToken auth =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(auth);

			}

			filterChain.doFilter(request, response);

		} catch (JwtException | IllegalArgumentException e) {
			log.error("JWT 처리 오류: {}", e.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰");
		} catch (BaseException e) {
			log.error("이메일 변경 후 재로그인 안한 사용자: {}", e.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰의 정보와 DB 정보 불일치");
		} catch (UsernameNotFoundException e) {
			log.error("존재하지 않는 사용자: {}", e.getMessage());
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "사용자를 찾을 수 없음");
		} catch (Exception e) {
			log.error("인증 처리 중 오류 발생", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류");
		}
	}
}

