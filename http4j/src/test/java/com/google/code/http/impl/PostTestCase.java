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

import org.testng.annotations.Test;

import com.google.code.http.Request;
import com.google.code.http.RequestTestCase;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public final class PostTestCase extends RequestTestCase {
	
	@Test(expectedExceptions = MalformedURLException.class)
	public void construct_cause_exception() throws MalformedURLException {
		new Post("code.google.com");
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void toMessage_cause_exception() throws MalformedURLException {
		Request request = createRequest("http://www.google.com");
		request.toMessage();
	}
	
	@Test
	public void toMessage() throws MalformedURLException {
		assertion("http://www.google.com/search?q=http4j", "POST /search HTTP/1.1\r\nHost:www.google.com\r\nContent-Length:8\r\n\r\nq=http4j");
		assertion("https://www.google.com:444/search?q=http4j&hl=en","POST /search HTTP/1.1\r\nHost:www.google.com:444\r\nContent-Length:14\r\n\r\nq=http4j&hl=en");
		assertion("http://localhost:8080/index.jsp;jsessionid=ABCDE?u=colin&pwd=http4j","POST /index.jsp;jsessionid=ABCDE HTTP/1.1\r\nHost:localhost:8080\r\nContent-Length:18\r\n\r\nu=colin&pwd=http4j");
	}

	@Override
	protected Request createRequest(String url) throws MalformedURLException {
		return new Post(url);
	}
}
