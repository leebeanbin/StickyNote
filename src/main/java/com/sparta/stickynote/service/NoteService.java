package com.sparta.stickynote.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.stickynote.dto.NoteRequestDto;
import com.sparta.stickynote.dto.NoteResponseDto;
import com.sparta.stickynote.entity.Note;
import com.sparta.stickynote.repository.NoteRepository;

import lombok.AllArgsConstructor;

// 실제 controller 와 repo 사이의 비즈니스 로직을 관리합니다.
@Service
@AllArgsConstructor // 모든 파라미터를 주입받을 수 있는 생성자를 만든다.
public class NoteService {
	private NoteRepository noteRepository;

	/**
	 * @param requestDto : String username, String title, String password, String content;
	 * @return noteResponseDto
	 */
	public Note createNote(NoteRequestDto requestDto) {
		// RequestDto -> Entity -> DB의 한줄을 차지합니다!
		var newNote = requestDto.toEntity();
		Note saveNote = noteRepository.save(newNote);

		return saveNote;
	}

	public Note getNote(Long noteNumber) {
		return noteRepository.findById(noteNumber)
			.orElseThrow(IllegalArgumentException::new);
	}

	public List<Note> getAllNote() {
		// TODO -- Repository 쿼리 기능 정리
		return noteRepository.findAll(Sort.by("createdAt").descending());
	}

	public List<NoteResponseDto> getNotesByKeyword(String keyword) {
		return noteRepository.findByContentContainsIgnoreCaseOrderByModifiedAtDesc(keyword).stream().map(NoteResponseDto::new).toList();
	}

	@Transactional
	public Note updateNote(Long id, NoteRequestDto requestDto) {
		Note note = checkPwdGetNote(id, requestDto.getPassword());

		note.update(requestDto);
		// 우리가 note에 dirty checking을 할 수 있게 Entity annotation
		// 을 통해서 EntityManager로 통해서 관리가 되기 때문에 update를 requestDto를 보내면 됩니다.
		// 그러면 요청한 객체가 전달이 됩니다.
		// 하지만, 해당 클래스에도 변경 감지가 되어야 변경을 확인 할 수 있습니다.
		return noteRepository.save(note);
	}

	private Note checkPwdGetNote(Long id, String password) {
		Note note = getNote(id);

		if(note.getPassword() != null
			&& !Objects.equals(note.getPassword(), password)){
			throw new IllegalArgumentException("You have problems on your password");
		}
		return note;
	}

	public void deleteNote(Long id , String password) {
		Note note = checkPwdGetNote(id, password);
		// note 삭제
		noteRepository.delete(note);
	}
}
