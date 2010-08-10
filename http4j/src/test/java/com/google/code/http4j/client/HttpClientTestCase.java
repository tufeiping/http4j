package com.google.code.http4j.client;


import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.HttpClient;
import com.google.code.http4j.client.HttpRequest;
import com.google.code.http4j.client.HttpResponse;
import com.google.code.http4j.client.impl.BasicHttpClient;
import com.google.code.http4j.client.impl.HttpGet;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HttpClientTestCase {
	
	private HttpClient client;
	
	@BeforeClass
	public void setUp() {
		client = new BasicHttpClient();
	}
	
	@Test
	public void testSubmit() throws IOException {
		HttpRequest request = new HttpGet("http://www.google.com/search?q=http4j");
		HttpResponse response = client.submit(request);
		Assert.assertNotNull(response);
	}
}
