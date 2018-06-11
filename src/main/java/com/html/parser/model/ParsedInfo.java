package com.html.parser.model;

import java.util.List;
import java.util.Map;


public class ParsedInfo {

	private String url;

	private String pageTitle;

	private String version;
	
	private boolean loginPage;
	
	private Map<String, Long>  headingLevels;
	
	private Map<LinkGroup, List<HyperMediaLink>> groupedHypermediaLinks;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public Map<String, Long> getHeadingLevels() {
		return headingLevels;
	}

	public void setHeadingLevels(Map<String, Long> headingLevels) {
		this.headingLevels = headingLevels;
	}

	public Map<LinkGroup, List<HyperMediaLink>> getGroupedHypermediaLinks() {
		return groupedHypermediaLinks;
	}

	public void setGroupedHypermediaLinks(Map<LinkGroup, List<HyperMediaLink>> groupedHypermediaLinks) {
		this.groupedHypermediaLinks = groupedHypermediaLinks;
	}

	@Override
	public String toString() {
		return
	            "Parsed Info [url = " + url +
	            ", page title = " + pageTitle +
	            ", version = " + version + 
	            ", heading levels = " + headingLevels.toString() +
	            ", grouped hypermedia links = " + groupedHypermediaLinks.toString() +
	            "]";
	}

	public boolean isLoginPage() {
		return loginPage;
	}

	public void setLoginPage(boolean loginPage) {
		this.loginPage = loginPage;
	}



}
