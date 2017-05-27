package com.mumoo.todo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mumoo.exception.APIException;

@RestController
@RequestMapping("todo")
public class TodoController {

	private TodoService todoService;

	TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping
	public List<TodoDTO> list() {

		return todoService.list();
	}

	@GetMapping("{id}")
	public TodoDTO get(@PathVariable Long id) throws APIException {
		return todoService.get(id);
	}

	@PostMapping
	public ResponseEntity<TodoDTO> create(HttpServletRequest request, @RequestBody TodoDTO dto) {
		Long id = this.todoService.create(dto);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.LOCATION, getURLBase(request) + "/todo/" + id);
		dto.setId(id);
		dto.setStatus(TodoStatus.PENDING);
		return new ResponseEntity<TodoDTO>(dto, responseHeaders, HttpStatus.CREATED);

	}

	@PutMapping("{id}")
	public ResponseEntity<TodoDTO> edit(@PathVariable Long id, @RequestBody TodoDTO dto) throws APIException {
		TodoDTO update = this.todoService.update(id, dto);

		return new ResponseEntity<TodoDTO>(update, HttpStatus.OK);
	}

	@PatchMapping("{id}")
	public void setStatus(@PathVariable Long id, @RequestParam TodoStatus status) throws APIException {
		this.todoService.setStatus(id, status);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) throws APIException {
		this.todoService.delete(id);
	}

	private String getURLBase(HttpServletRequest request) {

		URL requestURL;
		try {
			requestURL = new URL(request.getRequestURL().toString());
			String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
			return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
		} catch (MalformedURLException e) {
			return "";
		}

	}
}
