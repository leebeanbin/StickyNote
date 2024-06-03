package com.sparta.stickynote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sparta.stickynote.entity.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

	Optional<Comment> findByIdAndNoteIdAndDeletedFalse(Long id, Long noteId);

	List<Comment> findAllByNoteIdAndDeletedFalse(Long noteId);
}
