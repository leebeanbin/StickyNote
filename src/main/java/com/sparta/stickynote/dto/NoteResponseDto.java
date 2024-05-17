package com.sparta.stickynote.dto;

import com.sparta.stickynote.entity.Note;

import lombok.Getter;

@Getter
public class NoteResponseDto {
	private Long id; // 메모 별 고유 아이디를 생성한다.
	private String author;
	private String title;
	private String password;
	private String date;
	private String content;
	private String category;

	// entity 로 설정한 것 들에서 데이터를 받아 옵니다.
	// 또한 업데이트를 같은 클래스에 만들어서 내용을 변경하는 것에도 용이합니다.
	public NoteResponseDto(Note note) {
		this.id = note.getId();
		this.author = note.getAuthor();
		this.title = note.getTitle();
		this.password = note.getPassword();
		this.date = note.getDate();
		this.content = note.getContent();
		this.category = note.getCategory();
	}

	// 처음 등록을 할 때 데이터를 위의 정보를 다 입력 했을 때 전달 되도록 합니다!
	public NoteResponseDto(Long id, String author, String title, String password, String date, String content, String category){
		this.id = id;
		this.author = author;
		this.title = title;
		this.password = password;
		this.date = date;
		this.content = content;
		this.category = category;
	}
}
