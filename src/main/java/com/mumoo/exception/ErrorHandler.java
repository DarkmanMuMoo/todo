package com.mumoo.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
	private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

	@ExceptionHandler(APIException.class)
	public ResponseEntity<Map<String, String>> handleSQLException(HttpServletRequest request, APIException ex) {
		logger.error("APIException Occured:: At URL=" + request.getRequestURL() + " msg =" + ex.getMsg(),
				ex.getException());
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", ex.getMsg());
		return new ResponseEntity<Map<String, String>>(map, ex.getHttpStatus());
	}

}
