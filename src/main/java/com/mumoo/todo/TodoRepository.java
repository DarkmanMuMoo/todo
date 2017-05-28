package com.mumoo.todo;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {
	private static final String DELETE = "Delete from todo where id = ? ;";
	private static final String SELECT_FROM_TODO = "select * from todo ";
	private static final String GET = "select * from todo where id = ?";
	private static final String INSERT = "insert into todo values (null,?,?,?) ;";
	private JdbcTemplate template;

	@Autowired
	public TodoRepository(JdbcTemplate template) {
		this.template = template;
	}

	public List<Todo> list() {

		return template.query(SELECT_FROM_TODO, new BeanPropertyRowMapper<Todo>(Todo.class));

	}

	public Todo get(Long id) {

		Todo todo = template.queryForObject(GET, new Object[] { id }, new BeanPropertyRowMapper<Todo>(Todo.class));

		return todo;
	}

	public void update(Todo todo) {

		template.update("UPDATE todo SET subject = ?, content = ?  where id = ? ", todo.getSubject(), todo.getContent(),
				todo.getId());

	}

	public void setStatus(Long id, Integer status) {
		template.update("UPDATE todo SET status = ? where id = ? ", status, id);
	}

	public Long insert(Todo todo) {
		KeyHolder holder = new GeneratedKeyHolder();
		template.update(con -> {

			PreparedStatement pst = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, todo.getSubject());
			pst.setString(2, todo.getContent());
			pst.setInt(3, todo.getStatus());
			return pst;
		}, holder);

		return holder.getKey().longValue();
	}

	public void delete(Long id) {
		template.update(DELETE, id);
	}
}
