package com.sparta.stickynote.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

	// 해당 추상 클래스를 상속받는 클래스에서는 모두 creatAt, modifiedAt
	// 컬럼이 생성됩니다.
	// 단, 추상 클래스가 아니어도 구현이 가능합니다. 하지만, 우리가 해당 auditing 부분을
	// 구현하지 않기 때문에 추상 클래스로 만듭니다.
	@CreatedDate
	// 해당 updatable option은 생성 초기에만 시간을 저장하고 변경이 되었을 때 시간을 바꾸지 않기 위해서 입니다.
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;

	// 마지막 변경 시간을 저장합니다.
	@LastModifiedDate
	@Column
	// 자바의 데이트 타입을 매핑할 때 사용합니다.-> Date, Calendar, TIMESTAMP type 이 있습니다.
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime modifiedAt;
}



