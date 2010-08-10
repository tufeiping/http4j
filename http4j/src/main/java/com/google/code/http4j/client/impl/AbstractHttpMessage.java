package com.google.code.http4j.client.impl;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.code.http4j.client.HttpHeader;
import com.google.code.http4j.client.HttpMessage;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class AbstractHttpMessage implements HttpMessage {

	private static final long serialVersionUID = 3025166196954976484L;
	
	protected LinkedList<HttpHeader> headers;
	
	public AbstractHttpMessage() {
		this.headers = new LinkedList<HttpHeader>();
	}

	public List<HttpHeader> getHeaders() {
		return headers;
	}
	
	@Override
	public void addHeaders(Collection<HttpHeader> headers) {
		for(HttpHeader header:headers) {
			addHeader(header);
		}
	}
	
	@Override
	public void addHeaders(HttpHeader... headers) {
		for(HttpHeader header:headers) {
			addHeader(header);
		}
	}
	
	@Override
	public void addHeader(HttpHeader header) {
		boolean found = false;
		for(int i = 0, size = headers.size(); !found && i < size; i++) {
			if(found = headers.get(i).getName().equalsIgnoreCase(header.getName())) {
				headers.set(i, header);
			}
		}
		if(!found) {
			headers.add(header);
		}
	}

	@Override
	public void addHeader(String name, String value) {
		addHeader(createHeader(name, value));
	}
	
	protected HttpHeader createHeader(String name, String value) {
		return new BasicHttpHeader(name, value);
	}
}
