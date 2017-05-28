package com.mumoo.todo;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Category(value = IntegrationTest.class)
public class TodoControllerTest {
	private static final String APPLICATION_JSON = "application/json";
	@LocalServerPort
	int port;
	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setup() {
		RestAssured.port = this.port;
	}

	@Test
	public void testList_success_emptyResult() {
		given().when().get("/todo").then().statusCode(200).body("$", empty());
	}

	@Test
	public void testList_success_hasResult() {
		this.addTodo();
		given().when().get("/todo").then().statusCode(200).body("$", hasSize(1));
	}

	@Test
	public void testGet_notFound() {
		given().when().get("/todo/1").then().statusCode(404).body("msg", equalTo("not task found"));
	}

	@Test
	public void testGet_success() {
		Long id = this.addTodo("task1", "content1");
		given().when().get("/todo/" + id).then().statusCode(200).body("id", equalTo(Math.toIntExact(id)))
				.body("subject", equalTo("task1")).body("content", equalTo("content1"))
				.body("status", equalTo("PENDING"));
	}

	@Test
	public void testCreate_success() {
		TodoRequestDTO request = new TodoRequestDTO();
		request.setSubject("task1");
		request.setContent("content1");

		given().contentType(APPLICATION_JSON).body(request).when().post("/todo").then().statusCode(201)
				.header(HttpHeaders.LOCATION, containsString("/todo/")).body("id", not(empty()))
				.body("subject", equalTo("task1")).body("content", equalTo("content1"))
				.body("status", equalTo("PENDING"));
	}

	@Test
	public void testCreate_badParam() {
		given().contentType(APPLICATION_JSON).when().post("/todo").then().statusCode(400);
	}

	@Test
	public void testUpdate_success() {
		Long id = this.addTodo();
		TodoRequestDTO request = new TodoRequestDTO();
		request.setSubject("task1");
		request.setContent("content1");

		given().contentType(APPLICATION_JSON).body(request).when().put("/todo/" + id).then().statusCode(200)
				.body("id", equalTo(Math.toIntExact(id))).body("subject", equalTo("task1"))
				.body("content", equalTo("content1")).body("status", equalTo("PENDING"));

	}

	@Test
	public void testUpdate_notFound() {
		TodoRequestDTO request = new TodoRequestDTO();
		request.setSubject("task1");
		request.setContent("content1");

		given().contentType(APPLICATION_JSON).body(request).when().put("/todo/1").then().statusCode(404);
	}

	@Test
	public void testSetStatus_success() {
		Long id = this.addTodo();
		given().when().patch("/todo/" + id + "?status=DONE").then().statusCode(200);
		Todo todo = todoRepository.get(id);
		assertEquals(todo.getStatus(), TodoStatus.DONE.ordinal());

	}

	@Test
	public void testSetStatus_badParam() {
		Long id = this.addTodo();
		given().when().patch("/todo/" + id + "?status=SOME").then().statusCode(400);

	}

	@Test
	public void testSetStatus_notFound() {

		given().when().patch("/todo/1?status=DONE").then().statusCode(404);
	}

	@Test
	public void testDelete_success() {
		Long id = this.addTodo();
		given().when().delete("/todo/" + id).then().statusCode(200);
	}

	@Test
	public void testDelete_noFound() {
		given().when().delete("/todo/1").then().statusCode(404);
	}

	@After
	public void cleanUp() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "todo");
	}

	private Long addTodo() {
		return todoRepository.insert(new Todo(null, "subject", "content", TodoStatus.PENDING.ordinal()));

	}

	private Long addTodo(String subject, String content) {
		return todoRepository.insert(new Todo(null, subject, content, TodoStatus.PENDING.ordinal()));

	}

}
