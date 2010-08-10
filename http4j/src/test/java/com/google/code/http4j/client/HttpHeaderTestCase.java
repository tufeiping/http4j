/**
 * 
 */
package com.google.code.http4j.client;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.impl.BasicHttpHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HttpHeaderTestCase {
	
	HttpHeader host;
	HttpHeader userAgent;
	HttpHeader accept;

	@BeforeClass
	public void setUp() {
		host = createHeader("host", "www.google.com");
		userAgent = createHeader("user-agent", "Firefox 3.6 (Windows 7)");
		accept = createHeader("accept", "text/html");
	}

	@Test
	public void testGetName() {
		Assert.assertEquals(host.getName(), "host");
		Assert.assertEquals(userAgent.getName(), "user-agent");
		Assert.assertEquals(accept.getName(), "accept");
	}
	
	@Test
	public void testGetValue() {
		Assert.assertEquals(host.getValue(), "www.google.com");
		Assert.assertEquals(userAgent.getValue(), "Firefox 3.6 (Windows 7)");
		Assert.assertEquals(accept.getValue(), "text/html");
	}
	
	@Test
	public void testGetCanonicalName() {
		Assert.assertEquals(host.getCanonicalName(), "Host");
		Assert.assertEquals(userAgent.getCanonicalName(), "User-Agent");
		Assert.assertEquals(accept.getCanonicalName(), "Accept");
	}
	
	@Test
	public void testToCanonicalString() {
		Assert.assertEquals(host.toCanonicalString(), "Host:www.google.com");
		Assert.assertEquals(userAgent.toCanonicalString(), "User-Agent:Firefox 3.6 (Windows 7)");
		Assert.assertEquals(accept.toCanonicalString(), "Accept:text/html");
	}
	
	private HttpHeader createHeader(String name, String value) {
		return new BasicHttpHeader(name, value);
	}
}
