package com.sparta.stickynote.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
	@NotEmpty
	private String author;
	private String comment;

	public CommentRequestDto(String author, String comment) {
		this.author = author;
		this.comment = comment;
	}
}
