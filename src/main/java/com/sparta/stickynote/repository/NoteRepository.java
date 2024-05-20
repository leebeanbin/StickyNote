package com.sparta.stickynote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.stickynote.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	List<Note> findAppByOrderByModifiedAtDesc();
	List<Note> findByContentContainsIgnoreCaseOrderByModifiedAtDesc(String keyword);
}
