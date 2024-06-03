package com.sparta.stickynote.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.stickynote.dto.AuthRequest;
import com.sparta.stickynote.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class AuthController {

	@PostMapping("/authenticate")
	public String createToken(@RequestBody AuthRequest authRequest) throws Exception {
		if ("user".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())) {
			return JwtTokenProvider.generateToken(authRequest.getUsername());
		} else {
			throw new Exception("유효하지 않은 자격증명");
		}
	}

	@GetMapping("/validate")
	public String validateToken(@RequestHeader("Authorization") String token) {
		String username = JwtTokenProvider.extractUsername(token.substring(7));
		if (JwtTokenProvider.validateToken(token.substring(7), username)) {
			return "유효한 토큰";
		} else {
			return "유효하지 않은 토큰";
		}
	}
}
