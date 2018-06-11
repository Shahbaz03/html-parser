package com.html.parser.model;
/**
 * 
 * It presents type of hypermedia links
 *
 */
public enum LinkType {
	
	LINK("Link"), MEDIA("Media"), IMPORT("Import");
	
	private String value;
	
	LinkType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}

}
