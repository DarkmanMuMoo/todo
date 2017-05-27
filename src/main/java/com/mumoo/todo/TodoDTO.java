package com.mumoo.todo;

import lombok.Data;

public @Data class TodoDTO {
	private Long id;
	private String subject;
	private String content;
	private TodoStatus status;

}
