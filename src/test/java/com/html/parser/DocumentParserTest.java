package com.html.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URISyntaxException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import com.html.parser.exception.ParserException;
import com.html.parser.model.HTMLVersion;

public class DocumentParserTest {
	DocumentParser parser;
	
	@Before
	public void setUp(){
		parser = new DocumentParser();
	}
	
	@Test
	public void getTitleShouldReturnGoogle() throws Exception{
		Document document = parser.getDocument("http://google.com");
		String title = parser.getTitle(document);
		assertEquals(title,"Google");
	}
	
	@Test
	public void getTitleShouldReturnEmpty() throws Exception{
		String title = Jsoup.parse("<html><body></body></html>").title();
		assertEquals(title,"");
	}
	
	@Test
	public void getHtmlVersionShouldReturnHtml5() throws Exception{
		Response resp = Jsoup.connect("http://edition.cnn.com/").validateTLSCertificates(true).execute();
		System.out.println(resp.statusCode()+" -"+resp.statusMessage());
		Document document = parser.getDocument("http://stackoverflow.com");
		String version = parser.generateHtmlVersion(document.childNode(0));
		assertEquals(version,HTMLVersion.HTML5.getDescription());
	}
	
	@Test
	public void getHtmlVersionShouldReturnHtml4() throws Exception{
		Document doc = Jsoup.parse("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
		assertEquals(parser.generateHtmlVersion(doc.childNode(0)),HTMLVersion.HTML4.getDescription());
	}
	
	@Test
	public void getDomainNameShouldReturnStackOverflow() throws URISyntaxException{
		assertEquals(parser.getDomainName("http://stackoverflow.com"), "stackoverflow.com");
	}
	
	@Test
	public void isAbsoluteShouldReturnFalse() throws URISyntaxException{
		assertEquals(parser.isAbsolute("../home.html"), false);
	}
	@Test
	public void getHeadingsBylevelShouldReturnEmpty(){
		Document doc = Jsoup.parse("<html><body></body></html>");
		assertEquals(parser.getHeadingsBylevel(doc).size(), 0);
	}
	@Test
	public void getHeadingsBylevelShouldReturnOneForH1(){
		Document doc = Jsoup.parse("<html><body><h1>\"Hello World\"</h1></body></html>");
		assertEquals(parser.getHeadingsBylevel(doc).get("h1").longValue(), 1l);
	}
	
	@Test
	public void getHyperMediaLinksShouldReturnNotNull() throws ParserException{
		Document doc = Jsoup.parse("<html><body></body></html>");
		assertNotNull(parser.getHyperMediaLinks(parser.getDocument("http://stackoverflow.com"), "http://stackoverflow.com"));
	}

}
