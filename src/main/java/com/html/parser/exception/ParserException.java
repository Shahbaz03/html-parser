package com.html.parser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ParserException extends RuntimeException {
	static final long serialVersionUID = -3387516993334229948L;

	public ParserException(String message) {
		super(message);
	}

}
