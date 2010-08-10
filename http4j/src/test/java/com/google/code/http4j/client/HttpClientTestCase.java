package com.google.code.http4j.client;


import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.impl.BasicHttpClient;
import com.google.code.http4j.client.impl.HttpHead;

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
		HttpRequest request = new HttpHead("http://www.google.com");
		HttpResponse response = client.submit(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusLine());
		Assert.assertNotNull(response.getHeaders());
		Assert.assertTrue(response.getHeaders().size() > 0);
		Assert.assertNotNull(response.getEntity());
	}
}
