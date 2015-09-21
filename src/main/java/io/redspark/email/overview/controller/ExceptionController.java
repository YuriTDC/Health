package io.redspark.email.overview.controller;

import io.redspark.email.overview.dto.ExceptionDTO;
import io.redspark.email.overview.exceptions.EmailOverviewException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

	@ResponseBody
	@ExceptionHandler(EmailOverviewException.class)
	public ExceptionDTO exceptionHandler(EmailOverviewException exception, HttpServletResponse response){
		response.setStatus(exception.getStatusCode());
		return new ExceptionDTO(exception.getMessage(), exception.getStackTrace().toString());
	}
}
