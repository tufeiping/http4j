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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HttpHeadTestCase extends AbstractHttpUriRequestTestCase {
	
	@BeforeClass
	public void setUp() throws MalformedURLException, UnknownHostException {
		super.setUp();
	}
	
	@Test
	public void testGetHost() throws MalformedURLException, UnknownHostException {
		super.testGetHost();
	}
	
	@Test
	public void testFormatRequestLine() {
		super.testFormatRequestLine();
	}
	
	@Test
	public void testFormatBody() {
		super.testFormatBody();
	}
	
	@Test
	public void testFormatHeaders() {
		super.testFormatHeaders();
	}
	
	@Test
	public void testHasResponseEntity() {
		Assert.assertFalse(request.hasResponseEntity());
	}
	
	@Override
	protected AbstractHttpRequest createHttpRequest(String url)
			throws MalformedURLException, UnknownHostException {
		return new HttpHead(url);
	}
}
