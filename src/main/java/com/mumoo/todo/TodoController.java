package com.mumoo.todo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("todo")
public class TodoController {

	@GetMapping
	public List<String> list() {
	
		return null;
	}

	@GetMapping("{id}")
	public ResponseEntity<TodoDTO> get(@PathVariable Long id) {
		return null;
	}

	@PostMapping
	public ResponseEntity<TodoDTO> create() {
		return null;
	}

	@PutMapping("{id}")
	public ResponseEntity<TodoDTO> edit(@PathVariable Long id) {
		return null;
	}
    @PatchMapping("{id}")
	public void setStatus(@PathVariable Long id) {

	}
    @DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {

	}

}
