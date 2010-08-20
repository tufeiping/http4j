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
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.http4j.client.HeadersParser;
import com.google.code.http4j.client.Http;
import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.HttpResponse;
import com.google.code.http4j.client.HttpResponseParser;
import com.google.code.http4j.client.StatusCodes;
import com.google.code.http4j.client.StatusLine;
import com.google.code.http4j.client.StatusLineParser;
import com.google.code.http4j.client.impl.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicHttpResponseParser implements HttpResponseParser, Http {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public BasicHttpResponseParser() {
	}
	
	public HttpResponse parse(byte[] bytes, boolean parseEntity) throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		StatusLine statusLine = createStatusLineParser().parse(IOUtils.extractByEnd(buffer, LF));
		List<HttpHeader> headers = createHeadersParser().parse(IOUtils.extractByEnd(buffer, CR, LF, CR, LF));
		parseEntity &= StatusCodes.hasEntity(statusLine.getStatusCode());
		String entity = parseEntity ? parseEntity(buffer, statusLine.getStatusCode()) : null;
		return createHttpResponse(statusLine, headers, entity);
	}

	protected StatusLineParser createStatusLineParser() {
		return new BasicStatusLineParser();
	}
	
	protected HeadersParser createHeadersParser() {
		return new BasicHeadersParser();
	}

	protected String parseEntity(ByteBuffer buffer, int statusCode) {
		return "";//TODO
	}
	
	protected HttpResponse createHttpResponse(StatusLine statusLine, Collection<HttpHeader> headers, String entity) {
		HttpResponse response = new BasicHttpResponse(statusLine, entity);
		response.addHeaders(headers);
		logger.debug("HTTP Response <<\r\n{}", response);
		return response;
	}
}
