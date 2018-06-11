package com.html.parser.controller;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.html.parser.DocumentParser;
import com.html.parser.exception.ParserException;
import com.html.parser.model.ParsedInfo;
import com.html.parser.model.Search;

@RestController
@RequestMapping("/v1/parse")
public class RestLayer {
	@Autowired
	private DocumentParser parser;
	
	@PostMapping
	public ParsedInfo parse(@RequestBody Search search) {
		validate(search.getUrl());
		return parser.parse(search.getUrl());
	}
	
	private void validate(String url) {
		UrlValidator validator = new UrlValidator();
		if(!validator.isValid(url))
			throw new ParserException("Incorrect URL");
	}

}
