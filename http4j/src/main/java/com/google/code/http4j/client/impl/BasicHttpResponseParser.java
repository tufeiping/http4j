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


import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.HttpResponse;
import com.google.code.http4j.client.HttpResponseParser;
import com.google.code.http4j.client.StatusDictionary;
import com.google.code.http4j.client.StatusLine;

/**
 * Not ThreadSafe
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicHttpResponseParser implements HttpResponseParser {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public BasicHttpResponseParser() {
	}
	
	public HttpResponse parse(byte[] bytes, boolean hasEntity) throws IOException {
		StatusLine statusLine = parseStatusLine(bytes);
		Collection<HttpHeader> headers = parseHeaders(bytes);
		hasEntity &= StatusDictionary.hasEntity(statusLine.getStatusCode());
		String entity = hasEntity ? parseEntity(bytes, statusLine.getStatusCode()) : null;
		return createHttpResponse(statusLine, headers, entity);
	}

	protected HttpResponse createHttpResponse(StatusLine statusLine, Collection<HttpHeader> headers, String entity) {
		HttpResponse response = new BasicHttpResponse(statusLine, entity);
		response.addHeaders(headers);
		logger.debug("HTTP Response <<\r\n{}", response);
		return response;
	}

	protected StatusLine parseStatusLine(byte[] bytes) throws IOException {
		return null;
	}
	
	protected Collection<HttpHeader> parseHeaders(byte[] bytes) throws IOException {
		return null;
	}

	protected String parseEntity(byte[] bytes, int statusCode) {
		return "";//TODO
	}
}
