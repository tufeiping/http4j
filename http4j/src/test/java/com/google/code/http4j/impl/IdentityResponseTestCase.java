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

package com.google.code.http4j.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.Header;
import com.google.code.http4j.StatusLine;
import com.google.code.http4j.impl.HeadersParser;
import com.google.code.http4j.impl.IdentityResponse;
import com.google.code.http4j.impl.StatusLineParser;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class IdentityResponseTestCase {
	
	private StatusLine statusLine;
	
	private List<Header> headers;
	
	@BeforeClass
	public void beforeClass() throws IOException {
		statusLine = new StatusLineParser.BasicStatusLine("HTTP/1.1", 200, "OK");
		headers = new HeadersParser().parse("Content-Length:6\r\nContent-Type:text/html;charset=UTF-8\r\n".getBytes());
	}
	
	@Test(expectedExceptions = IOException.class)
	public void constructWithException() throws IOException {
		new IdentityResponse(statusLine, headers, new ByteArrayInputStream("http".getBytes()));
	}
}
