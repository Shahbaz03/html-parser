package com.html.parser.model;

/**
 * 
 * This class presents a model of hypermedia link  
 *
 */
public class HyperMediaLink {
	
	LinkGroup linkGroup;
	
	LinkType linkType;
	
	String tagName;
	
	String url;

	public LinkGroup getLinkGroup() {
		return linkGroup;
	}

	public void setLinkGroup(LinkGroup linkGroup) {
		this.linkGroup = linkGroup;
	}

	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return  "Hypermedia Link : [link group = " + linkGroup +
	            ", link type = " + linkType +
	            ", tag name = " + tagName +
	            ", url = " + url + "]";
	}

}
