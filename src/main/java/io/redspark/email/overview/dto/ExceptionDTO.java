package io.redspark.email.overview.dto;

public class ExceptionDTO {

	private String message;
	private String stacktrace;

	public ExceptionDTO() {
		super();
	}

	public ExceptionDTO(String message, String stacktrace) {
		super();
		this.message = message;
		this.stacktrace = stacktrace;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public String getMessage() {
		return message;
	}
}
