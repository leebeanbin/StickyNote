package com.sparta.stickynote.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class JwtConfig {

	@Value("${jwt.secret.key}")
	private String SECRET_KEY;

	@Value("${jwt.expiration}")
	private Long EXPIRATION;

	public static String staticSecretKey;
	public static Long staticExpiration;

	@PostConstruct
	public void init() {
		staticSecretKey = SECRET_KEY;
		staticExpiration = EXPIRATION;
	}
}