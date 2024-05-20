package com.sparta.stickynote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StickyNoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(StickyNoteApplication.class, args);
	}

}
