package com.sparta.stickynote.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sparta.stickynote.dto.NoteRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * 클래스에 대한 매개변수 없는 생성자를 자동으로 생성해줍니다.
 * 특별한 생성자 로직이 필요 없을 때 코드를 간소화하고 반복적인 코드를 줄이는 데 유용합니다.*/
@Entity // JPA manager를 통한 관리를 위한 애노테이션
@Getter
@Setter
@Table(name = "note") // mapping my table name
@NoArgsConstructor
public class Note extends Timestamped{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 메모 별 고유 아이디를 생성한다.
	@Column(name = "author", nullable = false)
	private String author;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "content", nullable = false, length = 500)
	private String content;

	@Builder
	public Note(String author, String title, String password, String content) {
		this.author = author;
		this.title = title;
		this.password = password;
		this.content = content;
	}

	public void update(NoteRequestDto requestDto){
		this.author = requestDto.getAuthor();
		this.title = requestDto.getTitle();
		this.content = requestDto.getContent();
	}
}
