package com.sparta.stickynote.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
	private String author;
	@NotBlank
	@Max(200)
	private String title;
	@NotBlank
	private String password;
	private String content;

	public Note toEntity(){
		return Note.builder()
			.author(author)
			.title(title)
			.password(password)
			.content(content)
			.build();
	}
}
