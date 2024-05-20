package com.sparta.stickynote.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.stickynote.dto.NoteRequestDto;
import com.sparta.stickynote.dto.NoteResponseDto;
import com.sparta.stickynote.entity.Note;
import com.sparta.stickynote.repository.NoteRepository;

@Service
public class NoteService {
	private NoteRepository noteRepository;

	public NoteService(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	public NoteResponseDto createNote(NoteRequestDto requestDto) {
		// RequestDto -> Entity -> DB의 한줄을 차지합니다!
		Note memo = new Note(requestDto);
		Note saveNote = noteRepository.save(memo);

		// Entity -> ResponseDto
		NoteResponseDto memoResponseDto = new NoteResponseDto(saveNote);

		return memoResponseDto;
	}

	public List<NoteResponseDto> getNotes() {
		return noteRepository.findAppByOrderByModifiedAtDesc().stream().map(NoteResponseDto::new).toList();
	}

	public List<NoteResponseDto> getNotesByKeyword(String keyword) {
		return noteRepository.findByContentContainsIgnoreCaseOrderByModifiedAtDesc(keyword).stream().map(NoteResponseDto::new).toList();
	}

	@Transactional
	public Long updateNote(Long id, NoteRequestDto requestDto) {
		Note note = getNoteById(id);

		if(note.getPassword().equals(requestDto.getPassword())){
			note.update(requestDto);
		}
		// 우리가 note에 dirty checking을 할 수 있게 Entity annotation
		// 을 통해서 EntityManager로 통해서 관리가 되기 때문에 update를 requestDto를 보내면 됩니다.
		// 그러면 요청한 객체가 전달이 됩니다.
		// 하지만, 해당 클래스에도 변경 감지가 되어야 변경을 확인 할 수 있습니다.
		return id;
	}

	public Long deleteNote(Long id , String password) {
		Note note = getNoteById(id);
		// note 삭제
		if(note.getPassword().equals(password)){
			noteRepository.delete(note);
		}
		return id;
	}

	// 해당 조회가 많이 사용되기 때문에 따로 private으로 빼서 사용합니다.
	private Note getNoteById(Long id) {
		// orElseThrow를 사용하면, Optional을 사용하지 않고 편하게 null 처리가 가능합니다.
		return noteRepository.findById(id).orElseThrow(() ->
			new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
		);
	}

}
