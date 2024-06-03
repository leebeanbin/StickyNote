package com.sparta.stickynote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.stickynote.dto.CommentRequestDto;
import com.sparta.stickynote.dto.CommentResponseDto;
import com.sparta.stickynote.entity.Comment;
import com.sparta.stickynote.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notes/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<CommentResponseDto> createComment(@RequestParam Long noteId, @RequestBody CommentRequestDto commentRequestDto) {
		CommentResponseDto responseDto = commentService.createComment(noteId, commentRequestDto);
		return ResponseEntity.ok().body(responseDto);
	}

	@GetMapping("/{noteId}")
	public ResponseEntity<CommentResponseDto> getComment(@Validated @PathVariable Long noteId, @RequestParam Long commentId) {
		CommentResponseDto comment = commentService.getComment(noteId, commentId);
		return ResponseEntity.ok().body(comment);
	}

	@GetMapping
	public ResponseEntity<List<CommentResponseDto>> getComments(@Validated @RequestParam Long noteId) {
		List<CommentResponseDto> comments = commentService.getCommnets(noteId);
		return ResponseEntity.ok().body(comments);
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<CommentResponseDto> updateComment(@RequestParam Long noteId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto){
		Comment comment = commentService.updateComment(noteId, commentId, commentRequestDto);
		CommentResponseDto responseDto = new CommentResponseDto(comment);
		return ResponseEntity.ok().body(responseDto);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity deleteComment(@RequestParam Long noteId, @PathVariable Long commentId) {
		CommentResponseDto comment = commentService.getComment(noteId, commentId);
		commentService.deleteComment(noteId, commentId, comment);
		return ResponseEntity.ok().build();
	}
}
