// package com.sparta.stickynote;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.sparta.stickynote.dto.NoteRequestDto;
// import com.sparta.stickynote.entity.Note;
// import com.sparta.stickynote.repository.NoteRepository;
// import com.sparta.stickynote.service.NoteService;
//
// @SpringBootTest
// @Transactional
// public class createNoteTest {
//
// 	@Autowired
// 	private NoteService noteService;
//
// 	// 실제로 noteRepository에 접근하지 않고 테스트 환경에서 동작을 하기 위한 애노테이션입니다.
// 	// 해당 애너테이션을 통해 스프링에 자동 주입되어 Bean으로 등록된다고 할 수 있습니다.
// 	@MockBean
// 	private NoteRepository noteRepository;
//
// 	private Note note;
//
// 	// 기본적인 Setup을 위해서,BeforeEach annotation을 사용해
// 	// data injection을 먼저 진행합니다.
// 	@BeforeEach
// 	void setUp() {
// 		// 테스트에 사용할 Note 인스턴스 생성
// 		note = new Note();
// 		note.setId(1L);
// 		note.setAuthor("Jake");
// 		note.setTitle("Original Title");
// 		note.setPassword("1234");
// 		note.setContent("Original Content");
// 		note.setCategory("Today's Tasks");
//
// 		// Mock 설정
// 		// Mock 설정을 통해서, 아이디가 1L인 객체를 호출 시 항상 note를 반환하도록 합니다.
// 		when(noteRepository.findById(1L)).thenReturn(java.util.Optional.of(note));
// 	}
//
// 	@Test
// 	@DisplayName("노트 업데이트 성공")
// 	void testUpdateNote() {
// 		// 변경할 데이터를 담은 DTO 생성
// 		NoteRequestDto requestDto = new NoteRequestDto();
// 		// requestDto.setAuthor("Jake");
// 		requestDto.setTitle("Updated Title");
// 		requestDto.setPassword("1234");
// 		requestDto.setContent("Updated Content");
// 		// requestDto.setCategory("Today's Tasks");
//
// 		// 업데이트 메소드 호출
// 		// Long updatedId = noteService.updateNote(1L, requestDto);
//
// 		// 통합테스트 -> 전체적인 테스트
// 		// 단위테스트(Unit Test) -> task 별로 쪼개서 단위별로 검증을 진행하는 것 이다. 이 부분이 중요하다.
// 		// 이 부분에 대해서는 현재 레벨에서는 구글링으로 예제를 찾아 학습을 하자 -> 해당 부분을 실무에서 정하는 하나의 약속이다.
//  		// given - when - then
//
// 		assertEquals(1L, updatedId);
// 		assertEquals("Updated Title", note.getTitle());
// 		assertEquals("Updated Content", note.getContent());
// 	}
//
// 	@Test
// 	@DisplayName("비밀번호 불일치로 인한 노트 업데이트 실패")
// 	void testUpdateNoteWithWrongPassword() {
// 		// 변경할 데이터와 잘못된 비밀번호를 담은 DTO 생성
// 		NoteRequestDto requestDto = new NoteRequestDto();
// 		requestDto.setAuthor("Jake");
// 		requestDto.setTitle("Updated Title");
// 		requestDto.setPassword("WrongPassword");
// 		requestDto.setContent("Updated Content");
// 		requestDto.setCategory("Today's Tasks");
//
// 		// 업데이트 메소드 호출
// 		Long updatedId = noteService.updateNote(1L, requestDto);
//
// 		// Assertions
// 		assertEquals(1L, updatedId);
// 		assertNotEquals("Updated Title", note.getTitle());
// 		assertNotEquals("Updated Content", note.getContent());
// 	}
//
// 	@Test
// 	@DisplayName("삭제 할 내용이 없는 경우")
// 	void testDeleteNote() {
// 		// 존재하지 않을 경우
// 		if(!noteRepository.existsById(1L)){
// 			// runtime exception의 하뤼 클래스로, 잘못된 인자가 전달될 가능성이 있는 경우 적절한 예외 처리를 통해 프로그램의
// 			// 안정성을 올릴 수 있습니다.
// 			assertThrows(IllegalArgumentException.class, ()->noteRepository.deleteById(1L));
// 		}
// 	}
// }
