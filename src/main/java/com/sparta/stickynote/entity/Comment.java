package com.sparta.stickynote.entity;

import com.sparta.stickynote.dto.CommentRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "author", nullable = false)
	private String author;
	@Column(name = "comment", nullable = false)
	private String comment;

	@ManyToOne
	@JoinColumn(name = "noteId")
	private Note note;

	@Column(name = "deleted", nullable = false)
	private Boolean deleted = Boolean.FALSE; // 기본값을 FALSE로 설정

	@Builder
	public Comment(String comment, String author, Note note) {
		this.comment = comment;
		this.author = author;
		this.note = note;
	}

	// Update는 댓글 내용만 수정 가능하게 한다.
	public void update(CommentRequestDto requestDto) {
		this.comment = requestDto.getComment();
	}

	public void softDelete() {
		this.deleted = true;
	}
}
