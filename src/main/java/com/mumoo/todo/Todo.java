package com.mumoo.todo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Todo {
	private Long id;
	private String subject;
	private String content;
	private int status;

	public Todo(Long id, String subject, String content, int status) {
		super();
		this.id = id;
		this.subject = subject;
		this.content = content;
		this.status = status;
	}

}
