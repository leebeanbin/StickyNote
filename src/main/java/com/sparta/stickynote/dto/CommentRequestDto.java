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
	private Note note;
	private String author;
	private String comment;

	public Comment toEntity(Note note) {
		return new Comment(note, this.author, this.comment);
	}
}
