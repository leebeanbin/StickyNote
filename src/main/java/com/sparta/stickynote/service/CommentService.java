package com.sparta.stickynote.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.stickynote.dto.CommentRequestDto;
import com.sparta.stickynote.dto.CommentResponseDto;
import com.sparta.stickynote.entity.Comment;
import com.sparta.stickynote.entity.Note;
import com.sparta.stickynote.repository.CommentRepository;
import com.sparta.stickynote.repository.NoteRepository;

@Service
public class CommentService {


	private final NoteRepository noteRepository;
	private final CommentRepository commentRepository;

	@Autowired
	public CommentService(CommentRepository commentRepository, NoteRepository noteRepository) {
		this.commentRepository = commentRepository;
		this.noteRepository = noteRepository;
	}

	public CommentResponseDto createComment(Long noteId, CommentRequestDto requestDto) {
		Note note = noteRepository.findById(noteId)
			.orElseThrow(() -> new RuntimeException("Note not found"));

		Comment newComment = new Comment(note.getId(), requestDto.getAuthor(), requestDto.getComment());

		Comment saveComment = commentRepository.save(newComment); // Entity -> Repo
		return new CommentResponseDto(saveComment); // Repo -> ResponseDto -> make the view
	}

	public CommentResponseDto getComment(Long noteId, Long commentId) {
		Comment comment = commentRepository.findByIdAndNoteId(commentId, noteId)
			.orElseThrow(() -> new RuntimeException("Comment not found or does not belong to the given note"));
		return new CommentResponseDto(comment);
	}

	// get all the comments by note id
	@Transactional(readOnly = true)
	public List<CommentResponseDto> getCommnets(Long noteId) {
		List<Comment> comments = commentRepository.findByNoteIdAndDeletedFalse(noteId);
		return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
	}

	//TODO - Excpetion 활용법 정리
	@Transactional
	public Comment updateComment(Long noteId, Long commentId, CommentRequestDto requestDto) {
		Comment comment = commentRepository.findByIdAndNoteIdAndDeletedFalse(commentId, noteId).orElseThrow(
			() -> new IllegalArgumentException("Failed to find comment with id," + noteId)
		);

		if(!comment.getAuthor().equals(requestDto.getAuthor())){
			throw new RuntimeException("You are not authorized to update this comment");
		}

		comment.update(requestDto);

		return comment;
	}


	// TODO : soft delete 정리
	public void deleteComment(Long noteId, Long commentId) {
		Comment comment = commentRepository.findByIdAndNoteIdAndDeletedFalse(commentId, noteId)
			.orElseThrow(() -> new RuntimeException("Comment not found or does not belong to the given note"));
		comment.softDelete(); // 논리적 삭제
		commentRepository.save(comment);
	}
}
