package com.sparta.stickynote.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.sparta.stickynote.entity.Note;
import com.sparta.stickynote.repository.NoteRepository;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class RepositoryTest {

	@Autowired
	private NoteRepository noteRepository;

	@Test
	public void findNoteTest(){
		Note note = new Note("user",
			"Test Note",
			"secret",
			"This is a test note."
		);
	}

}
