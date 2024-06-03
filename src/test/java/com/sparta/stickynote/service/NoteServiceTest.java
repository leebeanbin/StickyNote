package com.sparta.stickynote.service;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.sparta.stickynote.dto.NoteRequestDto;
import com.sparta.stickynote.dto.NoteResponseDto;
import com.sparta.stickynote.entity.Note;
import com.sparta.stickynote.repository.NoteRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class NoteServiceTest {

	@PersistenceContext
	EntityManager em ;

	/**
	 * @Mocks : 지정된 클래스의 모의 객체를 생성하여, 같은 동작을 수행하는 가짜라고 생각하면 됩니다.
	 * 		    @Mock을 사용하면 해당 클래스의 실제 인스턴스를 생성하지 않고도, 메소드 호출에 대한 기대값을 설정하거나 호출 정보를 검증할 수 있습니다.
	 * @InjectMock :  @Mock으로 생성된 모의 객체나 @Spy로 감싼 객체들을 자동으로 주입받아야 하는 테스트 대상 객체에 주입합니다. 즉, 테스트를 진행할 주체인 클래스에 필요한 의존성을 모의 객체로 채워 넣어 줍니다.
	 */
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NoteService noteService;

	@Autowired
	private NoteRepository noteRepository;

	@BeforeEach
	public void setup(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	@Transactional
	@DisplayName("노트 생성")
	void createNote() throws Exception {
		// given
		Note note = new Note("user",
			"Test Note",
			"secret",
			"This is a test note."
		);
		NoteRequestDto requestDto = new NoteRequestDto();
		requestDto.setUsername("user");
		requestDto.setTitle("Test Note");
		requestDto.setPassword("secret");
		requestDto.setContent("This is a test note.");


		NoteResponseDto responseDto = new NoteResponseDto(note);
		responseDto.setId(1L);
		responseDto.setUsername("user");
		responseDto.setTitle("Test Note");
		responseDto.setContent("This is a test note.");

		// when
		when(noteService.createNote(any(NoteRequestDto.class))).thenReturn(responseDto);


		// then
		MvcResult  result = mockMvc.perform(post("/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"user\",\"title\":\"Test Note\",\"password\":\"secret\",\"content\":\"This is a test note.\"}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username").value("user"))
			.andExpect(jsonPath("$.title").value("Test Note"))
			.andExpect(jsonPath("$.content").value("This is a test note."))
			.andReturn();

		verify(noteService).createNote(argThat(dto ->
			dto.getUsername().equals("user") &&
				dto.getTitle().equals("Test Note") &&
				dto.getPassword().equals("secret") &&
				dto.getContent().equals("This is a test note.")
		));
		// Output success message
		if (result.getResponse().getStatus() == HttpStatus.OK.value()) {
			System.out.println("Creation successful: " + result.getResponse().getContentAsString());
		}
	}



	@Test
	void deleteNote() {
	}
}