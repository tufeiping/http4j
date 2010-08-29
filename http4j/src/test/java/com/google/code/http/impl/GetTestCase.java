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

package com.google.code.http.impl;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public final class GetTestCase {
	
	@Test(expectedExceptions = MalformedURLException.class)
	public void construct_cause_exception() throws MalformedURLException {
		new Get("code.google.com/p/http4j");
	}
	
	@Test
	public void toMessage() throws MalformedURLException {
		assertion("http://www.google.com","GET / HTTP/1.1\r\nHost:www.google.com\r\n\r\n");
		assertion("http://www.google.com/search?q=http4j", "GET /search?q=http4j HTTP/1.1\r\nHost:www.google.com\r\n\r\n");
	}
	
	private void assertion(String url, String message) throws MalformedURLException {
		Get get = new Get(url);
		String m = get.toMessage();
		Assert.assertEquals(m, message);
	}
}
