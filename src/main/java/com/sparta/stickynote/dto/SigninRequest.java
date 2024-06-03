package com.sparta.stickynote.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninRequest{
	@NotBlank(message = "사용자 이름은 필수입니다.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$", message = "영어와 숫자 조합만 가능합니다.")
	private String username;

	@NotBlank(message = "비밀번호는 필수입니다.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$", message = "영어와 숫자 조합만 가능합니다.")
	private String password;
	private String authority;
}

