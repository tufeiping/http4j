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
