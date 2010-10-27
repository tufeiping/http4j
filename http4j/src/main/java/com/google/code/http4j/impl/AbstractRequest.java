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

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.google.code.http4j.HTTP;
import com.google.code.http4j.Header;
import com.google.code.http4j.Headers;
import com.google.code.http4j.Host;
import com.google.code.http4j.Request;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public abstract class AbstractRequest implements Request {

	private static final long serialVersionUID = 127059666172730925L;
	
	public static final String DEFAULT_USER_AGENT = "http4j v1.0";
	public static final String DEFAULT_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	public static final String DEFAULT_ACCEPT_ENCODING = "gzip,deflate";
	public static final String DEFAULT_CONNECTION_STRATEGY = "keep-alive";
	
	protected List<Header> headers;
	protected Host host;
	protected StringBuilder query;
	protected String path;
	protected URI uri;

	AbstractRequest(URL url) throws URISyntaxException {
		host = new BasicHost(url.getProtocol(), url.getHost(), url.getPort());
		uri = url.toURI();
		path = url.getPath().length() == 0 ? "/" : url.getPath();
		query = new StringBuilder(url.getQuery() == null ? "" : url.getQuery());
		headers = new LinkedList<Header>();
		setHeader(Headers.HOST, host.getAuthority());
		initDefaultHeaders();
	}

	abstract protected CharSequence formatBody();

	abstract protected CharSequence formatURI();

	abstract protected String getName();

	@Override
	public void setCookie(String value) {
		setHeader(Headers.REQUEST_COOKIE, value);
	}

	@Override
	public void addParameter(String name, String... values) {
		for (String value : values) {
			query = query.length() == 0 ? query : query.append('&');
			query.append(name).append('=').append(value);
		}
	}

	@Override
	public Host getHost() {
		return host;
	}

	@Override
	public void setHeader(String name, String value) {
		Header h = new CanonicalHeader(name, value);
		for (int i = 0, size = headers.size(); i < size; i++) {
			Header previous = headers.get(i);
			if (previous.getName().equals(h.getName())) {
				headers.set(i, h);
				return;
			}
		}
		headers.add(h);
	}

	@Override
	public URI getURI() {
		return uri;
	}

	@Override
	public void output(OutputStream out) throws IOException {
		byte[] message = toMessage();
		out.write(message);
		out.flush();
	}

	private byte[] toMessage() {
		StringBuilder m = formatRequestLine();
		m.append(formatHeaders());
		m.append(HTTP.CRLF).append(HTTP.CRLF).append(formatBody());
		String message = m.toString();
		HTTP.LOGGER.debug("Request:\r\n{}", message);
		return message.getBytes();
	}

	protected CharSequence formatHeaders() {
		StringBuilder m = new StringBuilder();
		for (Header h : headers) {
			m.append(HTTP.CRLF);
			m.append(h.toString());
		}
		return m;
	}

	private StringBuilder formatRequestLine() {
		StringBuilder l = new StringBuilder(getName());
		l.append(' ').append(formatURI());
		l.append(' ').append(HTTP.DEFAULT_VERSION);
		return l;
	}

	private void initDefaultHeaders() {
		setHeader(Headers.USER_AGENT, DEFAULT_USER_AGENT);
		setHeader(Headers.ACCEPT, DEFAULT_ACCEPT);
		setHeader(Headers.ACCEPT_ENCODING, DEFAULT_ACCEPT_ENCODING);
		setHeader(Headers.CONNECTION, DEFAULT_CONNECTION_STRATEGY);
	}

}
