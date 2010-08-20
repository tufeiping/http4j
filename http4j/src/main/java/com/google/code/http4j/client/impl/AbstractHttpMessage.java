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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.code.http4j.client.Http;
import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.HttpMessage;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class AbstractHttpMessage implements HttpMessage {

	private static final long serialVersionUID = 3025166196954976484L;

	protected List<HttpHeader> headers;

	public AbstractHttpMessage() {
		this.headers = new LinkedList<HttpHeader>();
	}

	public List<HttpHeader> getHeaders() {
		return headers;
	}

	@Override
	public void addHeaders(Collection<HttpHeader> headers) {
		for (HttpHeader header : headers) {
			addHeader(header);
		}
	}

	@Override
	public void addHeaders(HttpHeader... headers) {
		for (HttpHeader header : headers) {
			addHeader(header);
		}
	}

	@Override
	public void addHeader(String name, String value) {
		addHeader(createHeader(name, value));
	}

	@Override
	public void addHeader(HttpHeader header) {
		if (!headers.contains(header)) {
			headers.add(header);
		}
	}

	protected String formatHeaders() {
		StringBuilder message = new StringBuilder();
		for (HttpHeader header : headers) {
			message.append(header.format()).append(Http.CRLF);
		}
		return message.toString();
	}

	protected HttpHeader createHeader(String name, String value) {
		return new BasicHttpHeader(name, value);
	}
}
