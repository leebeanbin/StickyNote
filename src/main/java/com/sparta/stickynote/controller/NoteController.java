package com.sparta.stickynote.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.stickynote.CommonResponse;
import com.sparta.stickynote.dto.NoteRequestDto;
import com.sparta.stickynote.dto.NoteResponseDto;
import com.sparta.stickynote.entity.Note;
import com.sparta.stickynote.service.NoteService;

import lombok.RequiredArgsConstructor;

// @Controller -> thymleaf (ssr, Sever side Rendering ?)
// @RestController -> Json(Jackson) -> Object / Object -> Json (Client side rendering)
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

	private final NoteService noteService;

	//  임의로 SQL의 데이터를 넣어 확인
	// Mapping address에 / 만을 넣는 것은 RESTful API 규칙에 어긋난다.
	@PostMapping
	public ResponseEntity<CommonResponse<NoteResponseDto>> createNote(@RequestBody NoteRequestDto requestDto) {
		Note note = noteService.createNote(requestDto);
		NoteResponseDto responseDto = new NoteResponseDto(note);
		return ResponseEntity.ok().body(CommonResponse.<NoteResponseDto>builder()
				.statsCode(HttpStatus.OK.value())
				.msg("Success to build")
				.data(responseDto)
				.build()); // 만들고 바로 client에 전달하기 위함입니다.
	}

	// 단일 Note 조회
	@GetMapping("/{id}")
	public ResponseEntity<CommonResponse<NoteResponseDto>> getNote(Long noteNumber) {
		Note note = noteService.getNote(noteNumber);
		NoteResponseDto responseDto = new NoteResponseDto(note);
		return ResponseEntity.ok().body(CommonResponse.<NoteResponseDto>builder()
			.statsCode(HttpStatus.OK.value())
			.msg("Success to get a note")
			.data(responseDto)
			.build()); // 만들고 바로 client에 전달하기 위함입니다.
	}

	// Notes 조회
	@GetMapping
	public ResponseEntity<CommonResponse<List<NoteResponseDto>>> getNotes() {
		List<Note> notes = noteService.getAllNote();
		// TODO- 해당 부분의 기본적인 stream 분석
		List<NoteResponseDto> responseDtos = notes.stream()
			.map(NoteResponseDto::new)
			.collect(Collectors.toList());
		return ResponseEntity.ok().body(CommonResponse.<List<NoteResponseDto>> builder()
			.statsCode(HttpStatus.OK.value())
			.msg("Success to get a note")
			.data(responseDtos)
			.build());
	}

	@GetMapping("/")
	public List<NoteResponseDto> getNotesByKeyowrd(@RequestParam String keyword) {
		return noteService.getNotesByKeyword(keyword);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<CommonResponse<NoteResponseDto>> updateNote(@PathVariable Long id, @RequestBody NoteRequestDto requestDto) {
		Note note = noteService.updateNote(id, requestDto);
		NoteResponseDto responseDto = new NoteResponseDto(note);
		return ResponseEntity.ok().body(CommonResponse.<NoteResponseDto>builder()
			.statsCode(HttpStatus.OK.value())
			.msg("Success to update a note")
			.data(responseDto)
			.build()); // 만들고 바로 client에 전달하기 위함입니다.
	}

	@DeleteMapping("/")
	public ResponseEntity<CommonResponse> deleteNote(@RequestParam Long del, @RequestBody String password) {
		noteService.deleteNote(del, password);
		return ResponseEntity.ok().body(CommonResponse.builder()
			.statsCode(HttpStatus.OK.value())
			.msg("Success to delete a note")
			.build()); // 만들고 바로 client에 전달하기 위함입니다.
	}
}
