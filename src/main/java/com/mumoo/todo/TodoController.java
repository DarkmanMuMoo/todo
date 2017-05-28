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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Api("Todo Service")
@RestController
@RequestMapping("todo")
public class TodoController {

	private TodoService todoService;

	TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@ApiOperation(httpMethod = "GET", value = "List all of Task", response = TodoDTO.class, responseContainer = "List", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server Error") })
	@GetMapping
	public List<TodoDTO> list() {

		return todoService.list();
	}

	@ApiOperation(httpMethod = "GET", value = "Get task by ID", response = TodoDTO.class, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server Error"),
			@ApiResponse(code = 404, message = "no task found") })
	@GetMapping("{id}")
	public TodoDTO get(@ApiParam(value = "ID of task", required = true) @PathVariable Long id) throws APIException {
		return todoService.get(id);
	}

	@ApiOperation(httpMethod = "POST", responseHeaders = @ResponseHeader(name = "Location", description = "URL to get new create task", response = String.class), notes = "created task will has default status as PENDING", value = "Create Task", response = TodoDTO.class, produces = "application/json", consumes = "application/json", code = 201)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Task created", responseHeaders = {}),
			@ApiResponse(code = 500, message = "Server Error", responseHeaders = {}),
			@ApiResponse(code = 404, message = "no task found", responseHeaders = {}),
			@ApiResponse(code = 400, message = "data model not valid", responseHeaders = {}) })
	@PostMapping
	public ResponseEntity<TodoDTO> create(HttpServletRequest request, @RequestBody TodoRequestDTO dto) {
		Long id = this.todoService.create(dto);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.LOCATION, getURLBase(request) + "/todo/" + id);
		TodoDTO response = new TodoDTO(id, dto.getSubject(), dto.getContent(), TodoStatus.PENDING);
		return new ResponseEntity<TodoDTO>(response, responseHeaders, HttpStatus.CREATED);

	}

	@ApiOperation(httpMethod = "PUT", value = "update data of specific Task", response = TodoDTO.class, produces = "application/json", consumes = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server Error"),
			@ApiResponse(code = 404, message = "no task found") })
	@PutMapping("{id}")
	public ResponseEntity<TodoDTO> edit(@ApiParam(value = "ID of task", required = true) @PathVariable Long id,
			@RequestBody TodoRequestDTO dto) throws APIException {
		TodoDTO update = this.todoService.update(id, dto);

		return new ResponseEntity<TodoDTO>(update, HttpStatus.OK);
	}

	@ApiOperation(httpMethod = "PATCH", value = "Change status of Task")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server Error"),
			@ApiResponse(code = 404, message = "no task found"), @ApiResponse(code = 400, message = "invalid status") })
	@PatchMapping("{id}")
	public void setStatus(@ApiParam(value = "ID of task", required = true) @PathVariable Long id,
			@ApiParam(value = "Change Status value ", required = true) @RequestParam TodoStatus status)
			throws APIException {
		this.todoService.setStatus(id, status);
	}

	@ApiOperation(httpMethod = "DELETE", value = "Delete Task")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server Error"),
			@ApiResponse(code = 404, message = "no task found") })
	@DeleteMapping("{id}")
	public void delete(@ApiParam(value = "ID of task", required = true) @PathVariable Long id) throws APIException {
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
