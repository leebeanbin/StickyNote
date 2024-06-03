package com.sparta.stickynote.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sparta.stickynote.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

	private final JwtFilter jwtFilter;

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilterRegistrationBean() {
		FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(jwtFilter);
		registrationBean.addUrlPatterns("/notes/*");

		return registrationBean;
	}
}