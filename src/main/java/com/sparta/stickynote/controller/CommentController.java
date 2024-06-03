package com.sparta.stickynote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
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
import com.sparta.stickynote.entity.UserRoleEnum;
import com.sparta.stickynote.jwt.JwtUtil;
import com.sparta.stickynote.service.CommentService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/notes/comments")
public class CommentController {

	private final JwtUtil jwtUtil;
	private final CommentService commentService;

	public CommentController(CommentService commentService, JwtUtil jwtUtil) {
		this.commentService = commentService;
		this.jwtUtil = jwtUtil;
	}

	@GetMapping("/create-jwt")
	public String createJwt(HttpServletResponse res) {
		// Jwt 생성
		String token = jwtUtil.createToken("Robbie", UserRoleEnum.USER);

		// Jwt 쿠키 저장
		jwtUtil.addJwtToCookie(token, res);

		return "createJwt : " + token;
	}

	@GetMapping("/get-jwt")
	public String getJwt(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
		// JWT 토큰 substring
		String token = jwtUtil.substringToken(tokenValue);

		// 토큰 검증
		if(!jwtUtil.validateToken(token)){
			throw new IllegalArgumentException("Token Error");
		}

		// 토큰에서 사용자 정보 가져오기
		Claims info = jwtUtil.getUserInfoFromToken(token);
		// 사용자 username
		String username = info.getSubject();
		System.out.println("username = " + username);
		// 사용자 권한
		String authority = (String) info.get(JwtUtil.AUTHORIZATION_KEY);
		System.out.println("authority = " + authority);

		return "getJwt : " + username + ", " + authority;
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
