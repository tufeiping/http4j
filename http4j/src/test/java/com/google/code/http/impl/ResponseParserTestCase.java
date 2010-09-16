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

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http.Charset;
import com.google.code.http.HTTP;
import com.google.code.http.Header;
import com.google.code.http.Headers;
import com.google.code.http.Response;
import com.google.code.http.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class ResponseParserTestCase {
	
	private ResponseParser parser;
	
	private byte[] identity;
	
	private byte[] chunked;
	
	private byte[] chunkedWithTrailers;
	
	private byte[] noEntity;
	
	@BeforeClass
	public void beforeClass() {
		parser = new ResponseParser();
		identity = "HTTP/1.1 200 OK\r\nContent-Type:text/html; charset=UTF-8\r\nContent-Length:12\r\n\r\nHello World!".getBytes();
		chunked = "HTTP/1.1 200 OK\r\nContent-Type:text/html; charset=GBK\r\nTransfer-Encoding:chunked\r\n\r\n19\r\nHello World!-from http4j.\r\n1f\r\nauthor:guilin.zhang@hotmail.com\r\n0\r\n\r\n".getBytes();
		chunkedWithTrailers = "HTTP/1.1 200 OK\r\nContent-Type:text/html; charset=GBK\r\nTransfer-Encoding:chunked\r\n\r\n19\r\nHello World!-from http4j.\r\n1f\r\nauthor:guilin.zhang@hotmail.com\r\n0\r\nContent-Encoding:gzip\r\n\r\n".getBytes();
		noEntity = "HTTP/1.0 304 Not Modified\r\nContent-Type:text/html; charset=UTF-8\r\nContent-Length:12\r\n\r\nHello World!".getBytes();
	}
	
	@Test
	public void parseIdentity() throws IOException {
		Response response = parser.parse(identity);
		Assert.assertNotNull(response);
		StatusLine statusLine = response.getStatusLine();
		Assert.assertNotNull(statusLine);
		Assert.assertEquals(statusLine.getVersion(), HTTP.HTTP_1_1);
		Assert.assertEquals(statusLine.getStatusCode(), 200);
		Assert.assertEquals(statusLine.getReason(), "OK");
		List<Header> headers = response.getHeaders();
		Assert.assertNotNull(headers);
		Assert.assertEquals(headers.size(), 2);
		int contentLength = Headers.getContentLength(headers);
		Assert.assertEquals(contentLength, 12);
		byte[] entity = response.getEntity();
		Assert.assertEquals(new String(entity), "Hello World!");
		Assert.assertEquals(response.getCharset(), Charset.UTF_8);
	}
	
	@Test
	public void parseChunked() throws IOException {
		Response response = parser.parse(chunked);
		Assert.assertNotNull(response);
		StatusLine statusLine = response.getStatusLine();
		Assert.assertNotNull(statusLine);
		Assert.assertEquals(statusLine.getVersion(), HTTP.HTTP_1_1);
		Assert.assertEquals(statusLine.getStatusCode(), 200);
		Assert.assertEquals(statusLine.getReason(), "OK");
		List<Header> headers = response.getHeaders();
		Assert.assertNotNull(headers);
		Assert.assertEquals(headers.size(), 2);
		Assert.assertTrue(Headers.isChunked(headers));
		byte[] entity = response.getEntity();
		Assert.assertEquals(new String(entity), "Hello World!-from http4j.author:guilin.zhang@hotmail.com");
		Assert.assertEquals(response.getCharset(), Charset.GBK);
	}
	
	@Test
	public void parseChunkedWithTrailers() throws IOException {
		Response response = parser.parse(chunkedWithTrailers);
		Assert.assertNotNull(response);
		StatusLine statusLine = response.getStatusLine();
		Assert.assertNotNull(statusLine);
		Assert.assertEquals(statusLine.getVersion(), HTTP.HTTP_1_1);
		Assert.assertEquals(statusLine.getStatusCode(), 200);
		Assert.assertEquals(statusLine.getReason(), "OK");
		List<Header> headers = response.getHeaders();
		Assert.assertNotNull(headers);
		Assert.assertEquals(headers.size(), 3);
		Assert.assertTrue(Headers.isChunked(headers));
		Assert.assertEquals(Headers.getValueByName(headers, Headers.CONTENT_ENCODING), "gzip");
		byte[] entity = response.getEntity();
		Assert.assertEquals(new String(entity), "Hello World!-from http4j.author:guilin.zhang@hotmail.com");
		Assert.assertEquals(response.getCharset(), Charset.GBK);
	}
	
	@Test
	public void parseNoEntity() throws IOException {
		Response response = parser.parse(noEntity);
		Assert.assertNotNull(response);
		StatusLine statusLine = response.getStatusLine();
		Assert.assertNotNull(statusLine);
		Assert.assertEquals(statusLine.getVersion(), HTTP.HTTP_1_0);
		Assert.assertEquals(statusLine.getStatusCode(), 304);
		Assert.assertEquals(statusLine.getReason(), "Not Modified");
		List<Header> headers = response.getHeaders();
		Assert.assertNotNull(headers);
		Assert.assertEquals(headers.size(), 2);
		int contentLength = Headers.getContentLength(headers);
		Assert.assertEquals(contentLength, 12);
		Assert.assertNull(response.getEntity());
		Assert.assertEquals(response.getCharset(), Charset.UTF_8);
	}
}
