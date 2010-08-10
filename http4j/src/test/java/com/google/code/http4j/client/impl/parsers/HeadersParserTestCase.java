package com.google.code.http4j.client.impl.parsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.client.HttpHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HeadersParserTestCase {
	
	@Test
	public void testParse() throws IOException {
		InputStream input = new ByteArrayInputStream("Content-Type:text/html".getBytes());
		List<HttpHeader> headers = new HeadersParser().parse(input);
		Assert.assertNotNull(headers);
		Assert.assertEquals(headers.size(), 1);
		HttpHeader header = headers.get(0);
		Assert.assertEquals(header.getName(), "Content-Type");
		Assert.assertEquals(header.getValue(), "text/html");
	}
}
