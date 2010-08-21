package com.google.code.http4j.client.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.Http;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class URLEncodeTestCase {
	
	protected HttpGet get;
	
	@BeforeClass
	public void setUp() throws MalformedURLException, UnknownHostException, URISyntaxException {
		get = new HttpGet("www.google.com/search?q=张桂林&hl=en");
	}
	
	@Test
	public void testEncode() {
		String s = "张桂林";
		String encoded = get.encode(s);
		Assert.assertEquals(encoded, s);
		get.addHeader(Http.HEADER_NAME_ACCEPT_CHARSET, Http.UTF_8);
		encoded = get.encode(s);
		Assert.assertEquals(encoded, "%E5%BC%A0%E6%A1%82%E6%9E%97");
	}
	
	@Test(dependsOnMethods = "testEncode")
	public void testFormatMessage() throws UnsupportedEncodingException {
		String requestLine = get.formatRequestLine();
		Assert.assertEquals(requestLine, "GET /search?q=%E5%BC%A0%E6%A1%82%E6%9E%97&hl=en HTTP/1.1");
	}
}
