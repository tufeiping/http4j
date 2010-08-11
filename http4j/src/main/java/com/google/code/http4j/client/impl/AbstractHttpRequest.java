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
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.HttpRequest;
import com.google.code.http4j.client.HttpResponse;
import com.google.code.http4j.client.impl.parsers.HttpResponseParser;
import com.google.code.http4j.client.impl.utils.URLFormatter;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class AbstractHttpRequest extends AbstractHttpMessage implements
		HttpRequest {

	private static final long serialVersionUID = 7128951961154306746L;
	
	protected final URL url;
	
	protected final HttpHost host;
	
	protected final HttpResponseParser responseParser;

	public AbstractHttpRequest(String _url) throws MalformedURLException, UnknownHostException {
		super();
		url = URLFormatter.format(_url);
		host = new BasicHttpHost(url.getProtocol(), url.getHost(), url.getPort());
		addDefaultHeaders();
		responseParser = createHttpResponseParser();
	}

	protected void addDefaultHeaders() {
		addHeader(HEADER_NAME_HOST, url.getAuthority());
		addHeader(HEADER_NAME_USER_AGENT, DEFAULT_USER_AGENT);
	}

	@Override
	public String format() {
		StringBuilder message = new StringBuilder(formatRequestLine());
		message.append(CRLF).append(formatHeaders());
		message.append(CRLF).append(CRLF).append(formatBody());
		return message.toString();
	}
	
	@Override
	public HttpHost getHost() {
		return host;
	}
	
	@Override
	public HttpResponse parse(InputStream in) throws IOException {
		return responseParser.parse(in);
	}
	
	protected String getURI() {
		String file = url.getFile();
		return "".equals(file) ? "/" : file;
	}
	
	abstract protected String formatBody();
	abstract protected String formatRequestLine();
	abstract HttpResponseParser createHttpResponseParser();
}
