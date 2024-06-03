package com.sparta.stickynote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// TODO- EnableJpaRepositories 이 어노테이션이 뭔지 공부
@EnableJpaRepositories
@EnableJpaAuditing
@SpringBootApplication
public class StickyNoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(StickyNoteApplication.class, args);
	}

}
