package io.redspark.email.overview.exceptions;

import org.springframework.http.HttpStatus;

public class EmailOverviewException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int statusCode;
	private String message;

	public EmailOverviewException() {
		super();
	}

	public EmailOverviewException(HttpStatus status, String message) {
		super();
		this.statusCode = status.value();
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}


}
