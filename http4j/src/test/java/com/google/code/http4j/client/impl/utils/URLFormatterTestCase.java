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
	public void testFormat() throws MalformedURLException {
		assertion("www.google.com", "http://www.google.com");
		assertion("http://www.google.com", "http://www.google.com");
		assertion("https://www.google.com", "https://www.google.com");
		assertion("http://www.google.com/search?q=http4j", "http://www.google.com/search?q=http4j");
	}
	
	private void assertion(String input, String output) throws MalformedURLException {
		URL url = URLFormatter.format(input);
		Assert.assertNotNull(url);
		Assert.assertEquals(url.toString(), output);
	}
}
