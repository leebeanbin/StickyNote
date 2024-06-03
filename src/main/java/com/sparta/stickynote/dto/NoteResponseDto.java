package com.sparta.stickynote.dto;

import java.time.LocalDateTime;

import com.sparta.stickynote.entity.Note;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteResponseDto {
	private Long id; // 메모 별 고유 아이디를 생성한다.
	private String username;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	// entity 로 설정한 것 들에서 데이터를 받아 옵니다.
	// 또한 업데이트를 같은 클래스에 만들어서 내용을 변경하는 것에도 용이합니다.
	public NoteResponseDto(Note note) {
		this.id = note.getId();
		this.username = note.getUsername();
		this.title = note.getTitle();
		this.content = note.getContent();
		this.createdAt = note.getCreatedAt();
		this.modifiedAt = note.getModifiedAt();
	}
}
