package com.sparta.stickynote.dto;

import com.sparta.stickynote.entity.Note;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequestDto {
	@Email(message = "Invalid email form")
	private String username;
	@NotBlank
	@Max(200)
	private String title;
	// TODO -- NotBlank의 검증 범위를 파악하자.
	@NotBlank
	private String password;
	@Max(500)
	private String content;

	// Builder에 따른 Request에 들어온 값을 넣어 준 것 입니다.
	public Note toEntity(){
		return Note.builder()
			.username(username)
			.title(title)
			.password(password)
			.content(content)
			.build();
	}
}
