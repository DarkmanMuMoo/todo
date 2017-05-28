package com.mumoo.todo;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "Request of Task")
@Data
@NoArgsConstructor
public class TodoRequestDTO {

	@NotEmpty
	@ApiModelProperty(value = "subject of task", required = true)
	private String subject;
	@ApiModelProperty(value = "content of task")
	private String content;

	public TodoRequestDTO(String subject, String content) {
		super();

		this.subject = subject;
		this.content = content;

	}

}