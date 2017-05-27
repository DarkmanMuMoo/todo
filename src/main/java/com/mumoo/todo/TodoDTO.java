package com.mumoo.todo;

import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor class TodoDTO {
	private Long id;
	private String subject;
	private String content;
	private TodoStatus status;

	public TodoDTO(Long id, String subject, String content, TodoStatus status) {
		super();
		this.id = id;
		this.subject = subject;
		this.content = content;
		this.status = status;
	}

}
