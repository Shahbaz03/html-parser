package com.html.parser.controller;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.html.parser.DocumentParser;
import com.html.parser.exception.ParserException;
import com.html.parser.model.ParsedInfo;
import com.html.parser.model.Search;

@Controller
@RequestMapping("/")
public class ParseController {
	@Autowired
	private DocumentParser parser;
	
	@GetMapping
	public String searchForm(Model model){
		model.addAttribute("search", new Search());
        return "search";
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView parse(@ModelAttribute Search search) {
		validate(search.getUrl());
		ParsedInfo info = parser.parse(search.getUrl());
		ModelAndView mav = new ModelAndView("result");
        mav.addObject("result", info);
        return mav;
	}
	
	private void validate(String url) {
		UrlValidator validator = new UrlValidator();
		if(!validator.isValid(url))
			throw new ParserException("Incorrect URL");
	}

}
