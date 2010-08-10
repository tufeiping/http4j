package com.google.code.http4j.client.impl.parsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.client.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class StatusLineParserTestCase {
	
	@Test
	public void testParse() throws IOException {
		assertion("HTTP/1.1 200 OK", "HTTP/1.1", 200, "OK");
		assertion("HTTP/1.0 404 Not Found", "HTTP/1.0", 404, "Not Found");
		assertion("HTTP/1.1 204 No Content\r\n", "HTTP/1.1", 204, "No Content");
	}
	
	private void assertion(String line, String version, int responseCode, String reason) throws IOException {
		InputStream input = new ByteArrayInputStream(line.getBytes());
		StatusLine statusLine = new StatusLineParser().parse(input);
		Assert.assertNotNull(statusLine);
		Assert.assertEquals(statusLine.getVersion(), version);
		Assert.assertEquals(statusLine.getResponseCode(), responseCode);
		Assert.assertEquals(statusLine.getReason(), reason);
	}
}
