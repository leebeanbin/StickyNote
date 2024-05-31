package com.sparta.stickynote.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.stickynote.dto.NoteRequestDto;
import com.sparta.stickynote.dto.NoteResponseDto;
import com.sparta.stickynote.service.NoteService;

// @Controller -> thymleaf (ssr, Sever side Rendering ?)
// @RestController -> Json(Jackson) -> Object / Object -> Json (Client side rendering)
@RestController
@RequestMapping("/notes")
public class NoteController {

	private final NoteService noteService;

	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	//  임의로 SQL의 데이터를 넣어 확인
	@PostMapping("/")
	public NoteResponseDto createNote(@RequestBody NoteRequestDto requestDto) {
		return noteService.createNote(requestDto); // 만들고 바로 client에 전달하기 위함입니다.
	}

	@GetMapping("/findAll")
	public List<NoteResponseDto> getMemos() {
		return noteService.getNotes(); // 만들고 바로 client에 전달하기 위함입니다.
	}

	@GetMapping("/choice")
	public List<NoteResponseDto> getMemosByKeyowrd(@RequestParam String keyword) {
		return noteService.getNotesByKeyword(keyword);
	}

	@PutMapping("/update/{id}")
	public Long updateMemo(@PathVariable Long id, @RequestBody NoteRequestDto requestDto) {
		return noteService.updateNote(id, requestDto);
	}

	@DeleteMapping("/delete")
	public Long deleteMemo(@RequestParam Long id, @RequestParam String password) {
		return noteService.deleteNote(id, password);
	}
}
