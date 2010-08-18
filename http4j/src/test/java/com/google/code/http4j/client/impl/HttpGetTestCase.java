package com.google.code.http4j.client.impl;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HttpGetTestCase extends AbstractHttpUriRequestTestCase {
	
	@BeforeClass
	public void setUp() throws MalformedURLException, UnknownHostException {
		super.setUp();
	}
	
	@Test
	public void testGetHost() throws MalformedURLException, UnknownHostException {
		super.testGetHost();
	}
	
	@Test
	public void testHasResponseEntity() {
		Assert.assertTrue(request.hasResponseEntity());
	}

	@Override
	protected AbstractHttpRequest createHttpRequest(String url) throws MalformedURLException, UnknownHostException {
		return new HttpGet("http://www.google.com/search?q=http4j");
	}

	@Test
	public void testFormatBody() {
		super.testFormatBody();
	}

	@Test
	public void testFormatRequestLine() {
		super.testFormatRequestLine();
	}
}
