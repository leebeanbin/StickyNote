package com.sparta.stickynote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
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

@RestController
@RequestMapping("/notes/comments")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping
	public CommentResponseDto createComment(@RequestParam Long noteId, @RequestBody CommentRequestDto commentRequestDto) {
		return commentService.createComment(noteId, commentRequestDto);
	}

	@GetMapping
	public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam Long noteId) {
		List<CommentResponseDto> comments = commentService.getCommnets(noteId);
		return ResponseEntity.ok(comments);
	}

	@GetMapping("/{commentId}")
	public ResponseEntity<CommentResponseDto> getComment(@RequestParam Long noteId, @PathVariable Long commentId) {
		CommentResponseDto comment = commentService.getComment(noteId, commentId);
		return ResponseEntity.ok(comment);
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<Void> updateComment(@RequestParam Long noteId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto){
		Comment comment = commentService.updateComment(noteId, commentId, commentRequestDto);
		CommentResponseDto responseDto = new CommentResponseDto(comment);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@RequestParam Long noteId, @PathVariable Long commentId) {
		commentService.deleteComment(noteId, commentId);
		return ResponseEntity.ok().build();
	}
}
