package com.sparta.stickynote.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.stickynote.dto.NoteRequestDto;
import com.sparta.stickynote.dto.NoteResponseDto;
import com.sparta.stickynote.entity.Note;

@RestController
@RequestMapping("/api")
public class NoteController {
	private final JdbcTemplate jdbcTemplate;

	public NoteController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostMapping("/notes")
	public NoteResponseDto createNote(@RequestBody NoteRequestDto requestDto){
		// RequestDto는 입력칸에 입력 받은 정보들이 됩니다.
		Note note = new Note(requestDto);

		// Store them in DB
		KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

		String sql = "INSERT INTO note (author, title, password, date, content, category) VALUES (?, ?, ?, ?, ?, ?)";
		//  update 메서드를 사용하여 데이터베이스 업데이트 작업을 실행합니다.
		jdbcTemplate.update( con -> {
				// JdbcTemplate에서 제공하는 연결을 사용하여 PreparedStatement가 생성됩니다.
				// SQL 문 sql은 생성된 키를 반환하도록 준비되어 있으며, 이는 Statement.RETURN_GENERATED_KEYS로 지정됩니다.
				PreparedStatement preparedStatement = con.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
				// setString 메서드를 사용하여 PreparedStatement에 매개변수가 설정됩니다.
				preparedStatement.setString(1, note.getAuthor());
				preparedStatement.setString(2, note.getTitle());
				preparedStatement.setString(3, note.getPassword());
				preparedStatement.setString(4, note.getDate());
				preparedStatement.setString(5, note.getContent());
				preparedStatement.setString(6, note.getCategory());

				// JdbcTemplate에 의해 실행되도록 PreparedStatement가 반환됩니다.
				return preparedStatement;
			},
			keyHolder);
		// update 메서드에 KeyHolder 객체가 전달되어 데이터베이스에서 insert 문을 실행한 후 생성된 키를 보유합니다. 예를 들어 새로 삽입된 행의 ID를 검색하는 데 유용합니다.

		// DB Insert 후에 KeyHolder에 매핑된 키값을 가져옵니다.
		Long noteId = keyHolder.getKey().longValue();
		note.setId(noteId);

		// 해당 과정을 통해서 DB에 입력한 데이터를 전달하고, Dto클래스에 정보를 전달합니다.
		NoteResponseDto noteResponseDto = new NoteResponseDto(note);
		return noteResponseDto;
	}

	@GetMapping("/notes")
	public List<NoteResponseDto> getAllNotes() {
		// DB 조회
		String sql = "SELECT * FROM note";

		return jdbcTemplate.query(sql, new RowMapper<NoteResponseDto>() {
			@Override
			public NoteResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				// SQL 의 결과로 받아온 Memo 데이터들을 NoteResponseDto 타입으로 변환해줄 메서드
				Long id = rs.getLong("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String password = rs.getString("password");
				String date = rs.getString("date");
				String content = rs.getString("content");
				String category = rs.getString("category");
				return new NoteResponseDto(id, author, title, password, date, content , category);
			}
		});
	}
}
