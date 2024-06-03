package com.sparta.stickynote.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/notes/{noteId}/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	// noteId를 입력해서 service에서 유효한지 검증한다.
	@PostMapping
	public ResponseEntity<CommentResponseDto> createComment(
		@PathVariable(name = "noteId") Long noteId,
		@RequestBody CommentRequestDto commentRequestDto)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(
			commentService.createComment(noteId, commentRequestDto)
		);
	}

	@GetMapping("/{commentId}")
	public ResponseEntity<CommentResponseDto> getComment(
		@PathVariable(name = "noteId") Long noteId,
		@RequestParam(name = "commentId") Long commentId)
	{
		Comment comment = commentService.getComment(noteId, commentId);
		CommentResponseDto responseDto = new CommentResponseDto(
			comment.getId(),
			comment.getComment(),
			comment.getAuthor(),
			comment.getNote().getId(),
			comment.getCreatedAt()
		);
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	@GetMapping
	public ResponseEntity<List<CommentResponseDto>> getAllCommnet(
		@Validated @PathVariable(name = "noteId") Long noteId
	)
	{
		List<Comment> comments = commentService.getAllComment(noteId);
		List<CommentResponseDto> responseDtos = comments.stream()
			.map(comment -> new CommentResponseDto(
				comment.getId(),
				comment.getComment(),
				comment.getAuthor(),
				comment.getNote().getId(),
				comment.getCreatedAt()
			))
			.collect(Collectors.toList());
		return ResponseEntity.ok().body(responseDtos);
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<CommentResponseDto> updateComment(
		@PathVariable(name = "noteId") Long noteId,
		@PathVariable(name = "commentId") Long commentId,
		@Validated @RequestBody CommentRequestDto commentRequestDto){
		CommentResponseDto comment = commentService.updateComment(noteId, commentId, commentRequestDto);
		return ResponseEntity.ok().body(comment);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity deleteComment(
		@PathVariable(name = "noteId") Long noteId,
		@PathVariable(name = "commentId") Long commentId,
		@Validated @RequestBody CommentRequestDto requestDto) {
		commentService.deleteComment(noteId, commentId, requestDto);
		return ResponseEntity.ok().build();
	}
}
