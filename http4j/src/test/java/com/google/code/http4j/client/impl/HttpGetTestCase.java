package com.google.code.http4j.client.impl;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.HttpRequest;
import com.google.code.http4j.client.HttpRequestTestCase;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HttpGetTestCase extends HttpRequestTestCase {
	
	@BeforeClass
	public void setUp() throws MalformedURLException, UnknownHostException {
		super.setUp();
	}
	
	@Test
	public void testGetHost() throws MalformedURLException, UnknownHostException {
		Assert.assertEquals(request.getHost(), host);
	}
	
	@Override
	public void testHasEntity() {
		Assert.assertTrue(request.hasResponseEntity());
	}

	@Override
	protected HttpRequest createHttpRequest(String url) throws MalformedURLException, UnknownHostException {
		return new HttpGet("http://www.google.com/search?q=http4j");
	}
}
