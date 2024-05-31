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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id" , nullable = false)
	private Long id;
	@Column(name = "author" , nullable = false)
	private String author;
	@Column(name = "comment" , nullable = false)
	private String comment;

	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY) // 모든 상황에 필요한 것이 아니기 때문입니다.
	@JoinColumn(name = "noteId", nullable = false)
	private Note note;

	@Builder
	public Comment(Note note,String author, String comment) {
		this.note = note;
		this.author = author;
		this.comment = comment;
		this.createdAt = LocalDateTime.now();
	}

	public void update(CommentRequestDto requestDto){
		this.author = requestDto.getAuthor();
		this.comment = requestDto.getComment();
		this.createdAt = LocalDateTime.now();
	}
}
