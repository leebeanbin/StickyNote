package com.sparta.stickynote.dto;

import com.sparta.stickynote.entity.Comment;
import com.sparta.stickynote.entity.Note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
	private Long noteId;
	private String author;
	private String comment;

	public Comment toEntity(Long noteId) {
		return new Comment(noteId, this.author, this.comment);
	}
}
