package com.html.parser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Connection.KeyVal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.html.parser.exception.ParserException;
import com.html.parser.model.HTMLVersion;
import com.html.parser.model.HyperMediaLink;
import com.html.parser.model.LinkGroup;
import com.html.parser.model.LinkType;
import com.html.parser.model.ParsedInfo;

/**
 * HTML Document Parser
 * 
 * @author Shahbaz.Alam
 *
 */
@Component
public class DocumentParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentParser.class);
	
	private static final String HEADINGS = "h1, h2, h3, h4, h5, h6";
	
	public ParsedInfo parse(String url) {
		Document document = getDocument(url);
		ParsedInfo info = new ParsedInfo();
		info.setUrl(url);
		info.setPageTitle(getTitle(document));
		info.setVersion(getHtmlVersion(document));
		info.setLoginPage(isLoginPage(document));
		info.setHeadingLevels(getHeadingsBylevel(document));
		info.setGroupedHypermediaLinks(getHyperMediaLinks(document, info.getUrl()));
		return info;
	}
	
	public boolean isLoginPage(Document document) {
		if(document.location().contains("login")) {
			return true;
		}
		List<FormElement> forms = document.select("form").forms();
		for(FormElement form : forms) {
			List<KeyVal> list = form.formData();
			for(KeyVal keyval : list) {
				if(keyval.key().contains("password"))
					return true;
			}
		}
		return false;
	}

	public String getTitle(Document document) {
		return document.title();
	}
	
	public String getHtmlVersion(Document document) {
		return document.childNodes().stream().filter(node -> node instanceof DocumentType)
		.map(node -> generateHtmlVersion(node)).collect(Collectors.joining());
		
	}
	
	public String generateHtmlVersion(Node node) { 
		DocumentType documentType = (DocumentType) node; 
		return StringUtils.isEmpty(documentType.attr("publicid")) ? HTMLVersion.HTML5.getDescription() : HTMLVersion.HTML4.getDescription();
	}
	
	public Document getDocument(String url) {
		Document document = null;
		try {
			document = Jsoup
					.connect(url)
					.userAgent("Mozilla")
					.timeout(5000).get();
		} catch (IOException e) {
			LOGGER.error("Could not be created JSoup document "+ e.getMessage());
			throw new ParserException("Could not be created JSoup document.");
		}
		return document;
	}
	
	public Map<String, Long> getHeadingsBylevel(Document document) {
		Elements headingTags = document.select(HEADINGS);
		return headingTags
				.stream()
				.collect(Collectors.groupingBy(element -> element.tagName(), Collectors.counting()));
	}


	
	/**
	 * Hypermedia links in the document grouped into internal links
	 * to the same domain and external links to the other domains
	 * @param url
	 * @return
	 * @throws ParserException 
	 */
	public Map<LinkGroup, List<HyperMediaLink>> getHyperMediaLinks(Document document, String url) throws ParserException {
		Elements links = document.select("a[href]");
		Elements media = document.select("[src]");
		Elements imports = document.select("link[href]");
		Map<LinkGroup, List<HyperMediaLink>> groupedLinks = new HashMap<LinkGroup, List<HyperMediaLink>>();
		List<HyperMediaLink> hyperMediaLinks = new ArrayList<>();
		try {
			for (Element src : media) {
				HyperMediaLink hyperMediaLink = new HyperMediaLink();
				hyperMediaLink.setTagName(src.tagName());
				hyperMediaLink.setUrl(src.attr("abs:src"));
				hyperMediaLink.setLinkType(LinkType.MEDIA);
				identifyGroup(url, hyperMediaLink);
				hyperMediaLinks.add(hyperMediaLink);
			}
			
			for (Element link : imports) {
				HyperMediaLink hyperMediaLink = new HyperMediaLink();
				hyperMediaLink.setTagName(link.tagName());
				hyperMediaLink.setUrl(link.attr("abs:href"));
				hyperMediaLink.setLinkType(LinkType.IMPORT);
				identifyGroup(url, hyperMediaLink);
				hyperMediaLinks.add(hyperMediaLink);
	        }
			
			for (Element link : links) {
				HyperMediaLink hyperMediaLink = new HyperMediaLink();
				hyperMediaLink.setTagName(link.tagName());
				hyperMediaLink.setUrl(link.attr("abs:href"));
				hyperMediaLink.setLinkType(LinkType.LINK);
				identifyGroup(url, hyperMediaLink);
				hyperMediaLinks.add(hyperMediaLink);
	        }
			groupedLinks = hyperMediaLinks.stream().collect(Collectors.groupingBy(HyperMediaLink::getLinkGroup));
		} catch (URISyntaxException e) {
			LOGGER.error("Some hypermedia links could not be parsed. Please check the url. "+ e.getMessage());
			throw new ParserException("Some hypermedia links could not be parsed. Link=" + e.getInput());
		}
		return groupedLinks;
	}
	
	/**
	 * check is absolute
	 * @param url
	 * @return
	 * @throws URISyntaxException
	 */
	public boolean isAbsolute(String url) throws URISyntaxException{
		URI uri = new URI(url);
		return uri.isAbsolute();
	}
	
	/**
	 * get domain name
	 * @param url
	 * @return
	 * @throws URISyntaxException
	 */
	public String getDomainName(String url) throws URISyntaxException {
	    URI uri = new URI(url);
	    String domain = uri.getHost();
	    return domain.startsWith("www.") ? domain.substring(4) : domain;
	}
	
	/**
	 * check domain 
	 * @param url
	 * @param hyperLink
	 * @return
	 * @throws URISyntaxException
	 */
	public boolean checkDomain(String url, HyperMediaLink hyperLink) throws URISyntaxException{
		return getDomainName(url).equals(getDomainName(hyperLink.getUrl()));
	}
	
	
	/**
	 * identify is external or internal
	 * @param url
	 * @param hyperLink
	 * @throws URISyntaxException
	 */
	public void identifyGroup(String url, HyperMediaLink hyperLink) throws URISyntaxException{
		if(isAbsolute(url) && checkDomain(url, hyperLink))
			hyperLink.setLinkGroup(LinkGroup.INTERNAL);
		else
			hyperLink.setLinkGroup(LinkGroup.EXTERNAL);	
	}

}
