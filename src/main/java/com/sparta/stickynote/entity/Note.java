package com.sparta.stickynote.entity;

import com.sparta.stickynote.dto.NoteRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*
 * 클래스에 대한 매개변수 없는 생성자를 자동으로 생성해줍니다.
 * 특별한 생성자 로직이 필요 없을 때 코드를 간소화하고 반복적인 코드를 줄이는 데 유용합니다.*/
@Getter
@Setter
@NoArgsConstructor
public class Note {
	private Long id; // 메모 별 고유 아이디를 생성한다.
	private String author;
	private String title;
	private String password;
	private String date;
	private String content;
	private String category;

	public Note(NoteRequestDto requestDto) {
		this.author = requestDto.getAuthor();
		this.title = requestDto.getTitle();
		this.password = requestDto.getPassword();
		this.date = requestDto.getDate();
		this.content = requestDto.getContent();
		this.category = requestDto.getCategory();
	}

	public void update(NoteRequestDto requestDto){
		this.author = requestDto.getAuthor();
		this.title = requestDto.getTitle();
		this.password = requestDto.getPassword();
		this.date = requestDto.getDate();
		this.content = requestDto.getContent();
		this.category = requestDto.getCategory();
	}
}
