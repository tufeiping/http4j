package com.google.code.http4j.client;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.impl.BasicHeadersParser;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HeadersParserTestCase {

	private HeadersParser parser;

	@BeforeClass
	public void setUp() {
		parser = new BasicHeadersParser();
	}

	@Test
	public void testParse() throws IOException {
		assertion("Host:www.google.com", new NameValue("Host","www.google.com"));
		assertion("Host:www.google.com\r\nAccept:text/html", 
				new NameValue("Host","www.google.com"),
				new NameValue("Accept","text/html"));
		assertion("Host :\t www.google.com\r\nAccept:text/html", 
				new NameValue("Host","www.google.com"),
				new NameValue("accept","text/html"));
	}
	
	private void assertion(String source, NameValue... nvs) throws IOException {
		List<HttpHeader> headers = parser.parse(source.getBytes());
		Assert.assertNotNull(headers);
		Assert.assertEquals(headers.size(), nvs.length);
		int found = 0;
		for(NameValue nv:nvs) {
			for(HttpHeader header:headers) {
				if(header.getName().equalsIgnoreCase(nv.name)) {
					Assert.assertEquals(header.getValue(), nv.value);
					found++;
				}
			}
		}
		Assert.assertEquals(found, nvs.length);
	}
	
	static class NameValue {
		NameValue(String name, String value) {
			this.name = name;
			this.value = value;
		}
		String name;
		String value;
	}
}
