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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.code.http4j.Http;
import com.google.code.http4j.HttpHeader;
import com.google.code.http4j.HttpMessage;

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

	@Override
	public List<HttpHeader> getHeaders(String name) {
		List<HttpHeader> list = new ArrayList<HttpHeader>();
		for (HttpHeader header : headers) {
			if(name.equalsIgnoreCase(header.getName())) {
				list.add(header);
			}
		}
		return list;
	}

	@Override
	public HttpHeader getHeader(String name) {
		List<HttpHeader> list = getHeaders(name);
		return list.isEmpty() ? null : list.get(0);
	}
	
	@Override
	public void setHeader(HttpHeader header) {
		boolean found = false;
		for(int i = 0, size = headers.size(); !found && i < size; i++) {
			found = headers.get(i).getName().equalsIgnoreCase(header.getName());
			if(found) {
				headers.set(i, header);
			}
		}
		if(!found) {
			headers.add(header);
		}
	}
	
	@Override
	public void setHeader(String name, String value) {
		setHeader(createHeader(name, value));
	}
}
