package com.example.clothsdanawa.common.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.clothsdanawa.user.entity.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	private static final String BEARER_PREFIX = "Bearer ";
	private static final long TOKEN_TIME = TimeUnit.MINUTES.toMillis(1);
	private static final long REFRESH_TOKEN_TIME = TimeUnit.DAYS.toMillis(7);

	@Value("${jwt.secret.key}")
	private String secretKey;
	@Value("${jwt.secret.key1}")
	private String secretKey1;
	private Key key;
	private Key key1;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		byte[] bytes1 = Base64.getDecoder().decode(secretKey1);
		key = Keys.hmacShaKeyFor(bytes);
		key1 = Keys.hmacShaKeyFor(bytes1);
	}

	public String createToken(Long userId, String name, String email, UserRole userRole) {
		Date date = new Date();

		return BEARER_PREFIX + Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("name", name)
			.claim("email", email)
			.claim("userRole", userRole)
			.setExpiration(new Date(date.getTime() + TOKEN_TIME))
			.setIssuedAt(date)
			.signWith(key, signatureAlgorithm)
			.compact();
	}

	public String createRefreshToken(Long userId) {
		Date date = new Date();

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
			.setIssuedAt(date)
			.signWith(key1, signatureAlgorithm)
			.compact();
	}

	public String substringToken(String tokenValue) {
		if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
			return tokenValue.substring(7);
		}
		throw new NullPointerException("Not Found Token");
	}

	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Claims extractClaims1(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key1)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public boolean isTokenExpired(String refreshToken) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(key1)
			.build()
			.parseClaimsJws(refreshToken)
			.getBody();

		Date expiration = claims.getExpiration();
		return expiration.before(new Date());
	}
}

