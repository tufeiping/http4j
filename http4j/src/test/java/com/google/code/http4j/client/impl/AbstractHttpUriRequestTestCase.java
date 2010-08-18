package com.google.code.http4j.client.impl;

import org.testng.Assert;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class AbstractHttpUriRequestTestCase extends AbstractHttpRequestTestCase {

	public void testFormatRequestLine() {
		String message = abstractHttpRequest.formatRequestLine();
		Assert.assertEquals(message, abstractHttpRequest.getName() + " /search?q=http4j HTTP/1.1");
	}
	
	public void testFormatBody() {
		String message = abstractHttpRequest.formatBody();
		Assert.assertEquals(message, "");
	}
}
