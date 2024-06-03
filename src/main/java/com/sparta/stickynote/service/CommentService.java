package com.sparta.stickynote.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.stickynote.dto.CommentRequestDto;
import com.sparta.stickynote.dto.CommentResponseDto;
import com.sparta.stickynote.entity.Comment;
import com.sparta.stickynote.entity.Note;
import com.sparta.stickynote.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {


	private final CommentRepository commentRepository;
	private final NoteService noteService;

	@Transactional
	public CommentResponseDto createComment(Long noteId, CommentRequestDto requestDto) {
		// noteService에서 note가 유효한지 파악합니다.
		Note note = checkNote(noteId);
		// Entity -> author , comments
		Comment saveComment = commentRepository.save(new Comment(requestDto.getComment()
			,requestDto.getAuthor(), note
			));

		return CommentResponseDto.toDto(saveComment);
	}


	public Comment getComment(Long noteId, Long commentId) {
		checkNote(noteId);
		return commentRepository.findByIdAndNoteIdAndDeletedFalse(commentId, noteId)
			.orElseThrow(IllegalArgumentException::new);
	}

	// get all the comments by note id
	@Transactional(readOnly = true)
	public List<Comment> getAllComment(Long noteId) {
		// noteId의 유효성 확인
		checkNote(noteId);
		return commentRepository.findAllByNoteIdAndDeletedFalse(noteId);
	}

	//TODO - Excpetion 활용법 정리
	@Transactional
	public CommentResponseDto updateComment(Long noteId, Long commentId, CommentRequestDto requestDto) {
		Comment comment = checkPwdGetComment(noteId, commentId, requestDto);


		comment.update(requestDto);

		return CommentResponseDto.toDto(comment);
	}


	// TODO : soft delete 정리
	@Transactional
	public void deleteComment(Long noteId, Long commentId, CommentRequestDto requestDto) {
		Comment comment = checkPwdGetComment(noteId, commentId, requestDto);
		comment.softDelete(); // 논리적 삭제
		commentRepository.save(comment);
	}

	private Note checkNote(Long noteId) {
		// note가 있는지 없는지 여부 검사
		Note note = noteService.getNote(noteId);
		return note;
	}

	private Comment checkPwdGetComment(Long noteId, Long commentId, CommentRequestDto requestDto) {
		Comment comment = commentRepository.findByIdAndNoteIdAndDeletedFalse(commentId, noteId).orElseThrow(
			() -> new IllegalArgumentException("Failed to find comment with id," + noteId)
		);

		if(comment.getAuthor() != null
			&& !Objects.equals(comment.getAuthor(), requestDto.getAuthor())){
			throw new IllegalArgumentException("You are not authorized to update this comment.");
		}

		return comment;
	}
}
