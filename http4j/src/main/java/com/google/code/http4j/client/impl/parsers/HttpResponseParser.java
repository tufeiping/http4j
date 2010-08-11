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

package com.google.code.http4j.client.impl.parsers;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.HttpResponse;
import com.google.code.http4j.client.StatusLine;
import com.google.code.http4j.client.impl.BasicHttpResponse;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class HttpResponseParser implements Parser<HttpResponse>{
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected StatusLine statusLine;
	
	protected Map<String, HttpHeader> headerMap;
	
	protected String entity;
	
	public HttpResponse parse(InputStream in) throws IOException {
		statusLine = parseStatusLine(in);
		headerMap = parseHeaders(in);
		entity = parseEntity(in);
		return createHttpResponse();
	}

	protected HttpResponse createHttpResponse() {
		HttpResponse response = new BasicHttpResponse(statusLine, entity);
		response.addHeaders(headerMap.values());
		logger.debug("HTTP Response <<\r\n{}", response);
		return response;
	}

	protected StatusLine parseStatusLine(InputStream in) throws IOException {
		return new StatusLineParser().parse(in);
	}
	
	protected Map<String, HttpHeader> parseHeaders(InputStream in) throws IOException {
		return new HeadersParser().parse(in);
	}

	abstract protected String parseEntity(InputStream in);
}
