package com.sparta.stickynote.dto;

import lombok.Getter;

@Getter
public class NoteRequestDto {
	private String author;
	private String title;
	private String password;
	private String date;
	private String content;
	private String category;
}
