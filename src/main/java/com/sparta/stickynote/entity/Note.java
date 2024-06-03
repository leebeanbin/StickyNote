package com.sparta.stickynote.entity;

import java.util.List;

import com.sparta.stickynote.dto.NoteRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 클래스에 대한 매개변수 없는 생성자를 자동으로 생성해줍니다.
 * 특별한 생성자 로직이 필요 없을 때 코드를 간소화하고 반복적인 코드를 줄이는 데 유용합니다.*/
@Entity // JPA manager를 통한 관리를 위한 애노테이션
@Data
@Getter
@Setter
@Table(name = "note") // mapping my table name
// TODO-왜 이 녀석을 Protected로 설정하는지 이유를 찾기
@NoArgsConstructor
public class Note extends Timestamped{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id", nullable = false)
	private Long id; // 메모 별 고유 아이디를 생성한다.

	private String username;

	private String title;

	private String password;

	private String content;

	// note에서 Comment를 관리할 수 있게 만든다.
	@OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	List<Comment> comments;

	@Builder
	public Note(String username, String title, String password, String content) {
		this.username = username;
		this.title = title;
		this.password = password;
		this.content = content;
	}

	public void update(NoteRequestDto requestDto){
		this.username = requestDto.getUsername();
		this.title = requestDto.getTitle();
		this.content = requestDto.getContent();
	}
}
