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
import java.net.UnknownHostException;

import org.testng.Assert;

import com.google.code.http4j.client.Http;
import com.google.code.http4j.client.HttpRequestTestCase;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class AbstractHttpRequestTestCase extends HttpRequestTestCase {
	protected AbstractHttpRequest abstractHttpRequest;
	
	public void setUp() throws MalformedURLException, UnknownHostException {
		super.setUp();
		abstractHttpRequest = (AbstractHttpRequest) request;
	}
	
	public void testFormatHeaders() {
		String message = abstractHttpRequest.formatHeaders();
		Assert.assertEquals(message, "Host:www.google.com" + Http.CRLF + "User-Agent:" + Http.DEFAULT_USER_AGENT + Http.CRLF);
	}
	
	abstract public void testFormatBody();
	
	abstract public void testFormatRequestLine();

	@Override
	protected AbstractHttpRequest createHttpRequest(String url)
			throws MalformedURLException, UnknownHostException {
		return new HttpHead(url);
	}
}
