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

package com.google.code.http4j.client.impl;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.Http;
import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.HttpRequest;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class MessageHeaderTestCase {
	
	private HttpRequest message;
	
	@BeforeClass
	public void setUp() throws MalformedURLException, UnknownHostException, URISyntaxException {
		message = new HttpGet("www.baidu.com/s?wd=张桂林");
	}
	
	@Test
	public void testGetHeaders() {
		List<HttpHeader> headers = message.getHeaders();
		Assert.assertNotNull(headers);
		Assert.assertTrue(headers.size() > 0);// default headers
	}
	
	@Test
	public void testGetHeader() {
		HttpHeader header = message.getHeader(Http.HEADER_NAME_USER_AGENT);
		Assert.assertNotNull(header);
	}
	
	@Test
	public void testGetHeaders_String() {
		List<HttpHeader> headers = message.getHeaders(Http.HEADER_NAME_HOST);
		Assert.assertNotNull(headers);
		Assert.assertEquals(headers.size(), 1);// default headers
	}
	
	@Test
	public void testAddHeader_String_String() {
		message.addHeader(Http.HEADER_NAME_ACCEPT_CHARSET, Http.UTF_8);
		HttpHeader header = message.getHeader(Http.HEADER_NAME_ACCEPT_CHARSET);
		Assert.assertNotNull(header);
		Assert.assertEquals(header.getName(), Http.HEADER_NAME_ACCEPT_CHARSET);
		Assert.assertEquals(header.getValue(), Http.UTF_8);
	}
	
	@Test(dependsOnMethods = "testAddHeader_String_String")
	public void testSetHeader_String_String() {
		message.setHeader(Http.HEADER_NAME_ACCEPT_CHARSET, Http.ISO_8859_1);
		HttpHeader header = message.getHeader(Http.HEADER_NAME_ACCEPT_CHARSET);
		Assert.assertNotNull(header);
		Assert.assertEquals(header.getName(), Http.HEADER_NAME_ACCEPT_CHARSET);
		Assert.assertEquals(header.getValue(), Http.ISO_8859_1);
	}
}
