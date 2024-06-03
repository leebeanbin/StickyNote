package com.sparta.stickynote.dto;

import com.sparta.stickynote.entity.Comment;
import com.sparta.stickynote.entity.Note;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
	private Long noteId;
	private String author;
	private String comment;

	public Comment toEntity(Note note) {
		return Comment.builder()
			.note(note.getId()).build();
	}
}
