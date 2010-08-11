/**
 * Copyright (C) 2010 Zhang, Guilin <guilin.zhang@hotmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
	public void testFormat() {
		Assert.assertEquals(host.format(), "Host:www.google.com");
		Assert.assertEquals(userAgent.format(), "User-Agent:Firefox 3.6 (Windows 7)");
		Assert.assertEquals(accept.format(), "Accept:text/html");
	}
	
	private HttpHeader createHeader(String name, String value) {
		return new BasicHttpHeader(name, value);
	}
}
