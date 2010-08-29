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
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.google.code.http.HTTP;
import com.google.code.http.Header;
import com.google.code.http.Headers;
import com.google.code.http.Method;
import com.google.code.http.Request;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public abstract class AbstractRequest implements Request {

	private static final long serialVersionUID = 127059666172730925L;

	protected List<Header> headers;

	protected StringBuilder query;
	
	protected String path;

	public AbstractRequest(String url) throws MalformedURLException {
		this(new URL(url));
	}
	
	public AbstractRequest(URL url) {
		path = url.getPath().length() == 0 ? "/" : url.getPath();
		String q = url.getQuery() == null ? "" : url.getQuery();
		query = new StringBuilder(q);
		headers = new LinkedList<Header>();
		setHeader(Headers.HOST, url.getAuthority());
	}

	abstract protected Method getMethod();

	abstract protected CharSequence formatBody();

	abstract protected CharSequence formatURI();

	@Override
	public void addParameter(String name, String... values) {
		for (String value : values) {
			query = query.length() == 0 ? query : query.append('&');
			query.append(name).append('=').append(value);
		}
	}

	@Override
	public void setHeader(String name, String value) {
		Header h = new CanonicalHeader(name, value);
		boolean found = false;
		for (int i = 0, size = headers.size(); i < size; i++) {
			Header previous = headers.get(i);
			found = previous.getName().equals(h.getName());
			if (found) {
				headers.set(i, h);
				break;
			}
		}
		if (!found) {
			headers.add(h);
		}
	}

	@Override
	public final String toMessage() {
		StringBuilder m = formatRequestLine();
		m.append(formatHeaders());
		m.append(HTTP.CRLF).append(HTTP.CRLF).append(formatBody());
		return m.toString();
	}

	protected StringBuilder formatHeaders() {
		StringBuilder m = new StringBuilder();
		for (Header h : headers) {
			m.append(HTTP.CRLF);
			m.append(h.getName()).append(':').append(h.getValue());
		}
		return m;
	}

	private StringBuilder formatRequestLine() {
		StringBuilder l = new StringBuilder(getMethod().toString());
		l.append(' ').append(formatURI());
		l.append(' ').append(HTTP.DEFAULT_VERSION);
		return l;
	}
}
