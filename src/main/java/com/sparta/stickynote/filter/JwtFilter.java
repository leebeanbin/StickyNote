package com.sparta.stickynote.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.sparta.stickynote.jwt.JwtTokenProvider;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter implements Filter {

	// 원래는 모든 API를 실행하기 전에 Jwt 내부에 만들어 둔,
	// validate를 사용해야 합니다. 하지만, 같은 기능에 대해서는 그럴 필요가 없다.

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest servletRequest,
		ServletResponse servletResponse,
		FilterChain filterChain) throws IOException, ServletException {

		System.out.println("DO FILTER ");

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		// Header의 authorization 부분을 가져오는 것 입니다.
		String authorizationHeader = httpServletRequest.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			// 실제 Bearer 부분을 제거한 나머지를 가져옵니다.
			String token = authorizationHeader.substring(7);
			try {

				// Username 설정
				String username = JwtTokenProvider.extractUsername(token);

				// 검증 로직
				if (JwtTokenProvider.validateToken(token, username)) {
					httpServletRequest.setAttribute("username", username);
					filterChain.doFilter(servletRequest, servletResponse);
				} else {
					httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
				}
				// Bearer 부분을 자른것이 유효하지 않은 경우
			} catch (JwtException e) {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "토큰이 유효하지 않습니다.");
			}
		} else {
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "존재하지 않거나 유효하지 않은 Authorization 헤더");
		}
	}

	@Override
	public void destroy() {}
}