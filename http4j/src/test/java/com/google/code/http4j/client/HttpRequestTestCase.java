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
package com.google.code.http4j.client;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.impl.BasicHttpHost;
import com.google.code.http4j.client.impl.HttpGet;
import com.google.code.http4j.client.impl.HttpHead;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class HttpRequestTestCase {
	
	protected HttpHost host;
	protected HttpRequest getRequest;
	protected HttpRequest headRequest;

	@BeforeClass
	public void setUp() throws MalformedURLException, UnknownHostException {
		host = new BasicHttpHost("www.google.com");
		getRequest = new HttpGet("http://www.google.com/search?q=http4j");
		headRequest = new HttpHead("http://www.google.com");
	}
	
	@Test
	public void testGetHost() throws MalformedURLException, UnknownHostException {
		Assert.assertEquals(getRequest.getHost(), host);
		Assert.assertEquals(headRequest.getHost(), host);
	}
	
	@Test
	public void testHasEntity() {
		Assert.assertFalse(getRequest.hasEntity());
		Assert.assertFalse(headRequest.hasEntity());
	}
}
