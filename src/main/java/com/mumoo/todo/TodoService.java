package com.mumoo.todo;

import static com.mumoo.exception.APIException.notFound;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mumoo.exception.APIException;

@Service
@Transactional
public class TodoService {
	private TodoRepository todoRepository;

	@Autowired
	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public Long create(TodoDTO dto) {
		dto.setStatus(TodoStatus.PENDING);
		return todoRepository.insert(this.toEntity(dto));
	}

	public List<TodoDTO> list() {
		return todoRepository.list().stream().map(this::toDTO).collect(toList());
	}

	public TodoDTO update(Long id, TodoDTO update) throws APIException {
		Todo todo = checkExist(id);
		todo.setSubject(update.getSubject());
		todo.setContent(update.getContent());

		this.todoRepository.update(todo);

		return this.toDTO(todo);
	}

	public void setStatus(Long id, TodoStatus status) throws APIException {
		checkExist(id);
		this.todoRepository.setStatus(id, status.ordinal());
	}

	public TodoDTO get(Long id) throws APIException {
		Todo todo = checkExist(id);
		return this.toDTO(todo);
	}

	public void delete(Long id) throws APIException {
		checkExist(id);
		todoRepository.delete(id);
	}

	private Todo checkExist(Long id) throws APIException {
		try {
			return todoRepository.get(id);
		} catch (EmptyResultDataAccessException ex) {
			throw notFound("not task found");
		}
	}

	private Todo toEntity(TodoDTO dto) {
		return new Todo(null, dto.getSubject(), dto.getContent(), dto.getStatus().ordinal());
	}

	private TodoDTO toDTO(Todo entity) {
		return new TodoDTO(entity.getId(), entity.getSubject(), entity.getContent(),
				TodoStatus.values()[entity.getStatus()]);
	}
}
