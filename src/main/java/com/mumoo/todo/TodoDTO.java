package com.mumoo.todo;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(value = "Content of Task")
@Data
@NoArgsConstructor
public class TodoDTO {
	private Long id;
	@NotEmpty
	@ApiModelProperty(value = "subject of task", required = true)
	private String subject;
	@ApiModelProperty(value = "content of task")
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
