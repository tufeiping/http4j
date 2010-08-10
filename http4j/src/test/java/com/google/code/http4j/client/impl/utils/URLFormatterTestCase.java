package com.google.code.http4j.client.impl.utils;


import java.net.MalformedURLException;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.client.impl.utils.URLFormatter;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class URLFormatterTestCase {
	
	@Test
	public void testEnsureProtocol() {
		String url = URLFormatter.ensureProtocol("www.google.com");
		Assert.assertEquals(url, "http://www.google.com");
	}
	
	@Test
	public void testEnsurePort() throws MalformedURLException {
		URL http = URLFormatter.ensurePort("http://www.google.com");
		Assert.assertEquals(http.getPort(), 80);
		URL https = URLFormatter.ensurePort("https://www.google.com");
		Assert.assertEquals(https.getPort(), 443);
		URL specified = URLFormatter.ensurePort("http://www.google.com:8080");
		Assert.assertEquals(specified.getPort(), 8080);
	}
	
	@Test
	public void testFormat() throws MalformedURLException {
		assertion("www.google.com", "http://www.google.com:80");
		assertion("http://www.google.com", "http://www.google.com:80");
		assertion("https://www.google.com", "https://www.google.com:443");
		assertion("http://www.google.com/search?q=http4j", "http://www.google.com:80/search?q=http4j");
	}
	
	private void assertion(String input, String output) throws MalformedURLException {
		URL url = URLFormatter.format(input);
		Assert.assertNotNull(url);
		Assert.assertEquals(url.toString(), output);
	}
}
