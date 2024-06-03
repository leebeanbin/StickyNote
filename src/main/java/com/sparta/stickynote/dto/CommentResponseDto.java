package com.sparta.stickynote.dto;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.stickynote.entity.Comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {
	private Long id;
	private String author;
	private String comment;
	@JsonFormat(pattern =  "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
	private Long noteId;

	public
	CommentResponseDto(Long id, String author, String comment, Long noteId ,LocalDateTime createdAt) {
		this.id = id;
		this.comment = comment;
		this.author = author;
		this.noteId = noteId;
		this.createdAt = createdAt;
	}

	public static CommentResponseDto toDto(Comment saveComment) {
		return new CommentResponseDto(
			saveComment.getId(),
			saveComment.getComment(),
			saveComment.getAuthor(),
			saveComment.getNote().getId(),
			saveComment.getCreatedAt()
		);
	}
}
