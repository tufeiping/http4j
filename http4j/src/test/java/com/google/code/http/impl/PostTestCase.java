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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public final class PostTestCase {
	
	private Post post;
	
	@BeforeClass
	public void beforeClass() throws MalformedURLException {
		post = new Post("http://www.google.com/search?q=http4j");
	}
	
	@Test(expectedExceptions = MalformedURLException.class)
	public void construct_cause_exception() throws MalformedURLException {
		new Post("code.google.com");
	}
	
	@Test
	public void toMessage() {
		String m = post.toMessage();
		StringBuilder sb = new StringBuilder("POST /search HTTP/1.1\r\n");
		sb.append("Host:www.google.com\r\n");
		sb.append("Content-Length:").append("q=http4j".length()).append("\r\n");
		sb.append("\r\n");
		sb.append("q=http4j");
		Assert.assertEquals(m, sb.toString());
	}
}
