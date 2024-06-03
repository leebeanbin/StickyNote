package com.sparta.stickynote.entity;

import java.time.LocalDateTime;

import com.sparta.stickynote.dto.CommentRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
@RequiredArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "author", nullable = false)
	private String author;
	@Column(name = "comment", nullable = false)
	private String comment;

	private LocalDateTime createdAt;

	private Long noteId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "note_id", nullable = false)
	private Note note;

	@Column(name = "deleted", nullable = false)
	private Boolean deleted = Boolean.FALSE; // 기본값을 FALSE로 설정

	@Builder
	public Comment(Long noteId, String author, String comment) {
		this.noteId = noteId;
		this.author = author;
		this.comment = comment;
		this.createdAt = LocalDateTime.now();
	}

	// Update는 댓글 내용만 수정 가능하게 한다.
	public void update(CommentRequestDto requestDto) {
		this.comment = requestDto.getComment();
		this.createdAt = LocalDateTime.now();
	}

	public void softDelete() {
		this.deleted = true;
	}
}
