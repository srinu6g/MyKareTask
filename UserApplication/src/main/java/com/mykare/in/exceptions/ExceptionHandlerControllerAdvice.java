package com.mykare.in.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mykare.in.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	private static final String BAD_REQUEST = "Bad Request";

	Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody BaseResponse<String> handleResourceNotFound(final ResourceNotFoundException exception,
																	 final HttpServletRequest request) {

		BaseResponse<String> response = new BaseResponse<>();
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setDesc("Not Found");
		response.setBody(exception.getMessage());

		return response;
	}




	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody BaseResponse<String> handleBadRequest(final BadRequestException exception,
			final HttpServletRequest request) {

		BaseResponse<String> response = new BaseResponse<>();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setDesc(BAD_REQUEST);
		response.setBody(exception.getMessage());

		return response;
	}


	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public @ResponseBody BaseResponse<String> handleUnauthorizedRequest(final UnauthorizedException exception,
															   final HttpServletRequest request) {

		BaseResponse<String> response = new BaseResponse<>();
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setDesc(HttpStatus.UNAUTHORIZED.toString());
		response.setBody(exception.getMessage());

		return response;
	}



	@ExceptionHandler(JsonMappingException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody BaseResponse<String> handleJsonParseException(final JsonMappingException exception,
															final HttpServletRequest request) {

		BaseResponse<String> response = new BaseResponse<>();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setDesc(BAD_REQUEST);
		response.setBody(exception.getMessage());


		return response;
	}



	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody BaseResponse<String> handleException(final Exception exception,
			final HttpServletRequest request) {

		BaseResponse<String> response = new BaseResponse<>();
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setDesc("Internal Server Error");
		response.setBody(exception.getMessage());

		return response;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionResponse handleMethodArgumentNotValid(final MethodArgumentNotValidException exception, final HttpServletRequest request) {
		logger.error(exception.getLocalizedMessage());
		ExceptionResponse error = new ExceptionResponse();
		StringBuilder sb = new StringBuilder();
		exception.getBindingResult().getFieldErrors().forEach(x -> sb.append(x.getDefaultMessage() + ", "));
		error.setErrorMessage(sb.toString());
		error.callerURL(request.getRequestURI());
		return error;

	}

}