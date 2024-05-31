package com.sparta.stickynote.dto;


import java.time.LocalDateTime;

import com.sparta.stickynote.entity.Comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {
	private Long id;
	private String author;
	private String comment;
	private LocalDateTime createdAt;

	public CommentResponseDto(Comment comment) {
		this.id = comment.getId();
		this.author = comment.getAuthor();
		this.comment = comment.getComment();
		this.createdAt = comment.getCreatedAt();
	}

}
