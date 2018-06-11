package com.html.parser.model;
/**
 * 
 * It presents HTML versions 
 *
 */
public enum HTMLVersion {

	HTML5("HTML 5"), HTML4("HTML 4"), HTMLOld("Older Versions"), HTMLNo("Not found any version");

	private String description;

	HTMLVersion(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
