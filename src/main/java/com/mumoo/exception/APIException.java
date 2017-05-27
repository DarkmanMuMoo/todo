package com.mumoo.exception;

import org.springframework.http.HttpStatus;

public class APIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2180268520557475913L;

	private String msg;
	private HttpStatus httpStatus;
	private Exception exception;

	public APIException(String msg, HttpStatus httpCode, Exception exception) {
		super();
		this.msg = msg;
		this.httpStatus = httpCode;
		this.exception = exception;
	}

	public APIException(String msg, HttpStatus httpCode) {
		super();
		this.msg = msg;
		this.httpStatus = httpCode;
	}

	public static APIException notFound(String msg) {

		return new APIException(msg, HttpStatus.NOT_FOUND);

	}

	public static APIException notFound(String msg, Exception exception) {

		return new APIException(msg, HttpStatus.NOT_FOUND, exception);

	}

	public static APIException internalError(String msg, Exception exception) {
		return new APIException(msg, HttpStatus.INTERNAL_SERVER_ERROR, exception);
	}

	public static APIException internalError(String msg) {
		return new APIException(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public static APIException badRequest(String msg, Exception exception) {
		return new APIException(msg, HttpStatus.BAD_REQUEST, exception);
	}

	public static APIException badRequest(String msg) {
		return new APIException(msg, HttpStatus.BAD_REQUEST);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	@Override
	public String getMessage() {

		return this.exception == null ? "" : this.exception.getMessage();
	}

}